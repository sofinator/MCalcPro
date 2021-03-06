//Name: Sophia Nguyen
//This lab was done individually.
package ca.yorku.mcalcpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Locale;

import ca.roumani.i2c.MPro;

public class MCalcPro_Activity extends AppCompatActivity implements TextToSpeech.OnInitListener, SensorEventListener
{
    private TextToSpeech tts;

    private SensorManager mSensorManager;
    private Sensor accel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mcalcpro_layout);

        this.tts = new TextToSpeech(this, this);

        // Get sensor manager
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // Get the default sensor of specified type
        accel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public void buttonClicked(View v) {
        String principalS = ((EditText) findViewById(R.id.pBox)).getText().toString();
        String amortizationS = ((EditText) findViewById(R.id.aBox)).getText().toString();
        String interestS = ((EditText) findViewById(R.id.iBox)).getText().toString();

        try
        {
            MPro mp = new MPro();
            mp.setPrinciple(principalS);
            mp.setAmortization(amortizationS);
            mp.setInterest(interestS);

            String s = "Monthly  Payment = " + mp.computePayment("%,.2f");
            s+="\n\n";
            s+="By making this payments monthly for " + mp.getAmortization() + " years, the mortgage will be paid in full. But if you terminate the mortgage on its nth anniversary, the balance still owing depends on n as shown below:";
            s+= "\n\n";
            s+= String.format("%8s","n") + String.format("%16s","Balance");
            s+= "\n\n";
            int amortization = Integer.parseInt(mp.getAmortization());

            for (int n=0; n <= amortization; n++) {
                s+= String.format("%8d",n) + mp.outstandingAfter(n,"%,16.0f");
                s+= "\n\n";
            }

            ((TextView) findViewById(R.id.output)).setText(s);

            tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
        }
        catch (Exception e)
        {
            // display e.getMessage()
            Toast label = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
            label.show();
        }

    }

    public void onInit(int status)
    {
        this.tts.setLanguage(Locale.US);
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        double ax = event.values[0];
        double ay = event.values[1];
        double az = event.values[2];

        double a = Math.sqrt(ax*ax + ay*ay + az*az);

        if (a > 20) {
            ((EditText) findViewById(R.id.pBox)).setText("");
            ((EditText) findViewById(R.id.aBox)).setText("");
            ((EditText) findViewById(R.id.iBox)).setText("");
            ((TextView) findViewById(R.id.output)).setText("");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accel!=null) {
            mSensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (accel!=null) {
            mSensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1)
    {

    }
}
