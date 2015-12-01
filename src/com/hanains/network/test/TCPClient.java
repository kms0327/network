package com.hanains.network.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPClient {
	private static final String SERVER_IP = "192.168.1.21";
	private static final int SERVER_PORT = 5050;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket socket = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;

		//1.소켓 생성
		socket = new Socket();

		try{
			//2.서버연결
			socket.connect(new InetSocketAddress(SERVER_IP,SERVER_PORT));	
			System.out.println("Client Connect Success");

			//3.IOStream 받아오기
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();

			//4.쓰기/읽기
			String data = "Hello world";
			outputStream.write(data.getBytes("UTF-8"));

			byte[] buffer = new byte[256];
			int readByteCount = inputStream.read(buffer);

			data = new String (buffer,0,readByteCount,"UTF-8");
			System.out.println(">>"+data);

		}catch(IOException e){
			System.out.println("Client Error");
		}finally{
			try{
				if(inputStream != null){
					inputStream.close();
				}
				if(outputStream != null){
					outputStream.close();
				}
				if(socket != null && socket.isClosed() ==false){
					try{
						socket.close();
					}catch(IOException ex){
						ex.printStackTrace();
					}
				}
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}

	}

}
