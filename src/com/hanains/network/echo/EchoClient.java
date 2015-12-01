package com.hanains.network.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
	private static final String SERVER_IP = "192.168.1.21";
	private static final int SERVER_PORT = 5050;

	public static void main(String[] args) {
		Socket socket = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;

		Scanner scanner = new Scanner(System.in);
		
		try {
			// 1.소켓 생성
			socket = new Socket();
			// 2.서버연결
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			System.out.println("Client Connect Success");

			// 3.IOStream 받아오기
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();

			while(true){
				System.out.print(">>");
				String me = scanner.nextLine();
				
				if(me.equals("exit")){
					break;
				}
				outputStream.write(me.getBytes("UTF-8"));
				outputStream.flush();

				byte[] buffer = new byte[256];
				int readByteCount = inputStream.read(buffer);
				me = new String(buffer, 0, readByteCount, "UTF-8");
				System.out.println("<<" + me);
			}
		} catch (IOException e) {
			System.out.println("Client Error");
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
				if (socket != null && socket.isClosed() == false) {
					try {
						socket.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}
}
