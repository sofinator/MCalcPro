package ca.yorku.mcalcpro;

import org.junit.Test;

import ca.roumani.i2c.MPro;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest
{
    @Test
    public void addition_isCorrect()
    {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void mProTest() {
        MPro mp = new MPro();
        mp.setPrinciple("400000");
        mp.setAmortization("20");
        mp.setInterest("5");

        String s = "Monthly  Payment = " + mp.computePayment("%,.2f");
        s+="\n\n";
        s+="By making this payments monthly for " + mp.getAmortization() + " years, the mortgage will be paid in full. But if you terminate themortgage on its nth anniversary, the balance still owing depends on n as shown below:";
        s+= "\n\n";
        s+= String.format("%8s","n") + String.format("%16s","Balance");
        s+= "\n\n";
        int amortization = Integer.parseInt(mp.getAmortization());

        for (int n=0; n <= amortization; n++) {
            s+= String.format("%8d",n) + mp.outstandingAfter(n,"%,16.0f");
            s+= "\n\n";
        }

        System.out.println(s);
    }
}

