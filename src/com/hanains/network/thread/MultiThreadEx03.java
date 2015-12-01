package com.hanains.network.thread;

public class MultiThreadEx03 {

	public static void main(String[] args) {
		Thread thread = new Thread(new DigitRunnableimple());
		thread.start();
		
		for (char c = 'A'; c <= 'Z'; c++) {
			System.out.print(c);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
