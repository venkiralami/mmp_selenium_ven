package org.iitwf.heathcare.mmp;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
    	//String str = "Hello I'm your String";

			String str = "Time : 12Pm";
			str = str.trim();
    	String[] splited = str.split(" ");
    	System.out.println(splited);
    	//var splited = str.split(" ");
    	String splited1=str.split(" ")[0]; //Hello
    	String splited2=splited[1]; //I'm
    	String splited3=splited[2]; //your
    	//var splited4=splited[3]; //String
        assertTrue(true);
    }
}
