package com.hanains.network.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	private static final int PORT = 5050;
	public static void main(String[] args) {
		ServerSocket serverSocket = null;

		try {
			// 1.서버소켓생성
			serverSocket = new ServerSocket();
			// 2.바인딩
			InetAddress inetAddress = InetAddress.getLocalHost();
			String localhost = inetAddress.getHostAddress();

			serverSocket.bind(new InetSocketAddress(localhost, PORT));
			System.out.println("[서버]바인딩" + localhost + ":" + PORT);

			// 3.연결요청 대기(요청올때까지 Blocking)
			while(true){
				Socket socket = serverSocket.accept();// 요청을 받아들이기 위한 서버소켓
				Thread thread = new EchoServerReciveThread(socket);
				thread.start();
			}
			
		}catch(Exception e){
			System.out.println(e);
		}finally{
			if (serverSocket != null && serverSocket.isClosed() == false) {
				try {
					// 5.서버소켓닫기
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
