package com.hanains.network.chat;

import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class mainThread {
	private static final int PORT = 5050;
	public static void main(String[] args){
		ServerSocket serverSocket = null;
		Socket socket = null;
		//PrintWriter담을 수 있는 List 생성
		List<Writer> listWriters = new ArrayList<Writer>();
		
		try {
			//1.서버 소켓 생성
			serverSocket =new ServerSocket();
			//2.바인딩
			String hostAddress = InetAddress.getLocalHost().getHostAddress();
			serverSocket.bind(new InetSocketAddress(hostAddress,PORT));
			log("연결 기다림 " + hostAddress +":"+PORT);
			
			//3.클라이언트 연결(다중연결)
			while(true){
				socket = serverSocket.accept();
				Thread thread = new ChatServerThread(socket,listWriters);
				thread.start();
			}
		} catch (IOException e) {
			System.out.println("mainThread_1 Error : "+e);
		}finally{
			if (serverSocket != null && serverSocket.isClosed() == false) {
				try {
					// 5.서버소켓닫기
					serverSocket.close();
				} catch (IOException e) {
					System.out.println("mainThread_2 Error : "+e);
				}
			}
		}
	}
	//출력
	public static void log(String log) {
		System.out.println("[chat-client] " + log);
	}
}
