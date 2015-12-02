package com.hanains.network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class EchoServerReciveThread extends Thread {
	private Socket socket;
	
	public EchoServerReciveThread(Socket socket){
		this.socket = socket;
	}
	
	@Override
	public void run() {

		BufferedReader bufferReader = null;
		PrintWriter printWriter = null;
		
		try{
			// 4.연결성공시
			InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			String remoteHostAddress = inetSocketAddress.getAddress().getHostAddress();
			int remoteHostPort = inetSocketAddress.getPort();
			System.out.println("[서버] 연결됨 from " + remoteHostAddress + " : " + remoteHostPort);
			
			// 5.IOStream 받아오기
			bufferReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			printWriter = new PrintWriter(socket.getOutputStream());
			
			//3. 데이터 읽기
			char[] buffer = new char[256];
			while( true ) {
			/*	int readCharCount = bufferReader.read( buffer );
				if( readCharCount < 0 ) {
				//	EchoServer.consolLog( "클라이언트로 부터 연결 끊김" );
					break;
				}*/
				String data = bufferReader.readLine();
				if(data == null){
					break;
				}
				printWriter.println(data);
				printWriter.flush();
			}
		}catch(Exception ex){
			System.out.println(ex);
		}finally{
			try {
				if (bufferReader != null) {
					bufferReader.close(); //inputStream 닫히면 socket도 닫힌다
				}
				if (printWriter != null) {
					printWriter.close();
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
