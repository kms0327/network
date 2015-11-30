package com.hanains.netwrok.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHost {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			InetAddress inetAddress = InetAddress.getLocalHost();
			
			System.out.println("Host 이름 : " + inetAddress.getHostName());
			System.out.println("Host IP : " + inetAddress.getHostAddress());
			
			byte[] addresses = inetAddress.getAddress();
			
			for(int i =0 ; i<addresses.length; i++){
				System.out.print(addresses[i] & 0xff);
				if(i+1<addresses.length){
					System.out.print(".");
				}
			}
		}catch(UnknownHostException ex){
			ex.printStackTrace();
		}
	}

}
