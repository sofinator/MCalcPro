//Name: Sophia Nguyen
//This lab was done individually.
package ca.yorku.mcalcpro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ca.roumani.i2c.MPro;

public class MCalcPro_Activity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mcalcpro_layout);
    }

    public void buttonClicked(View v) {
        String principalS = ((EditText) findViewById(R.id.pBox)).getText().toString();
        String amortS = ((EditText) findViewById(R.id.aBox)).getText().toString();
        String interestS = ((EditText) findViewById(R.id.iBox)).getText().toString();

        MPro mp = new MPro();
        mp.setPrinciple(principalS);
        mp.setAmortization(amortS);
        mp.setInterest(interestS);

        String s = "Monthly  Payment = " + mp.computePayment("%,.2f");
        s+="\n\n";
        s+="By making this payments monthly for ";
        s+= "\n\n";
        s+= String.format("%8d",0) + mp.outstandingAfter(0,"%,16.0f");
        s+= "\n\n";
        s+= String.format("%8d",1) + mp.outstandingAfter(1,"%,16.0f");

        ((TextView) findViewById(R.id.output)).setText(s);
    }
}
