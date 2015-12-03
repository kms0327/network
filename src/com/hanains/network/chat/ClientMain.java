package com.hanains.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientMain {
	private static final String SERVER_IP = "192.168.1.21";
	private static final int SERVER_PORT = 5050;

	public static void main(String[] args) {
		Socket socket = null;
		
		BufferedReader bufferReader = null;
		PrintWriter printWriter = null;
		Scanner scanner = new Scanner(System.in);
		
		try {
			// 1.소켓 생성
			socket = new Socket();
			// 2.서버연결
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			System.out.println("Client Connect Success");

			// 3.bufferReader = 받는거 / printWriter = 입력해서 보내는거
			bufferReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),StandardCharsets.UTF_8),true);

			// 5. join 프로토콜
			System.out.print("닉네임>>");
			String nickname = scanner.nextLine();
			printWriter.println("join:" + nickname);
			printWriter.flush();
			bufferReader.readLine();
			
						
			// 6. ChatClientRecevieThread 시작
			Thread thread = new chatClientReceiveThread(bufferReader);
			thread.start();
			
			while (true) {
				System.out.print(">>");
				String me = scanner.nextLine();

				if ("quit".equals(me) == true) {
					printWriter.println("quit");
					printWriter.flush();
					break;
				} else {
					printWriter.println( "message:" + me );
					printWriter.flush();
				}
			}
			
			bufferReader.close(); 
			printWriter.close();
			scanner.close();
			if (socket != null && socket.isClosed() == false) {
				try {
					socket.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		} catch (IOException e) {
			System.out.println("Client Error");
		} 
	}
}
