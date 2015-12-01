package com.hanains.network.thread;

import java.util.ArrayList;
import java.util.List;

public class DigitThread extends Thread {

	private List list = new ArrayList();
	
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
