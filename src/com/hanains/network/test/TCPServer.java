package com.hanains.network.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
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
			Socket socket = serverSocket.accept();// 요청을 받아들이기 위한 서버소켓

			// 4.연결성공시
			InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			String remoteHostAddress = inetSocketAddress.getAddress().getHostAddress();
			int remoteHostPort = inetSocketAddress.getPort();
			System.out.println("[서버] 연결됨 from " + remoteHostAddress + " : " + remoteHostPort);

			// 5.IOStream 받아오기
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();

			// 6.data 보내기
			// String data = "hello World\r\n";
			// outputStream.write(data.getBytes("UTF-8"));
			// outputStream.flush();

			// 6.data 읽기
			try {
				byte[] buffer = new byte[256];
				while (true) {
					int readByteCount = inputStream.read(buffer);
					if (readByteCount < 0) {
						System.out.println("[server] client disconnect");
						break;
					}
					String data = new String(buffer, 0, readByteCount);
					System.out.println("[서버] 수신 데이터 : " + data);

					outputStream.write(data.getBytes("UTF-8"));
					outputStream.flush();
				}
			} catch (IOException ex) {
				System.out.println("[server] error :" + ex);
			} finally {
				// 8.자원정리
				inputStream.close();
				outputStream.close();

				if (socket.isClosed() == false) {
					socket.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// serverSocket이 생성하다가 Exception 발생했을 걸 대비
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
