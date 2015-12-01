package com.hanains.netwrok.util;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class NSLookup {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//반복문 제어 변수
		boolean exitControl = true;
		Scanner scanner = null;
		try{
			//키보드 입력 변수
			scanner = new Scanner(System.in);
			while(exitControl){
				System.out.print(">");
				String hostname = scanner.nextLine();

				if("exit".equals(hostname)){
					exitControl = false;
				}else{
					InetAddress inetAddress[] = InetAddress.getAllByName(hostname);

					for(int i = 0; i<inetAddress.length; i++){
						System.out.println(hostname+" : "+inetAddress[i].getHostAddress());
					}
				}
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			scanner.close();
		}
		
	}
	
}
