package com.hanains.network.thread;

public class LowerCaseAlphaThread extends Thread {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (char c = 'a'; c <= 'z'; c++) {
			System.out.print(c);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
