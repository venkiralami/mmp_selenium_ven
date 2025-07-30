/**
 * 
 */
package org.iitwf.healthcare.mmp;

import java.util.Random;

/**
 * 
 */
public class DynamicNumberAndCharGeneration {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		for(int i=0;i<=100; i++) {
			int num = generateRandomNumber(90, 100);
			System.out.println(num);
		}
	}

	public static int generateRandomNumber(int min, int max) {
		return new Random().nextInt(max - min + 1) + min;
	}

}
