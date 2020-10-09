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
        s+="By making this payments monthly for ";
        s+= "\n\n";
        s+= String.format("%8d",0) + mp.outstandingAfter(0,"%,16.0f");
        s+= "\n\n";
        s+= String.format("%8d",1) + mp.outstandingAfter(1,"%,16.0f");

        System.out.println(s);
    }
}

