package com.hanains.network.thread;

public class DigitRunnableimple implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i = 0; i<=10; i++){
			System.out.print(i);
			try{
				Thread.sleep(1000);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}

}
