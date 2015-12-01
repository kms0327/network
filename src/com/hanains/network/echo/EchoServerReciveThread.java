package com.hanains.network.echo;

import java.io.BufferedReader;
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
		// TODO Auto-generated method stub
		try{
			// 4.연결성공시
			InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			String remoteHostAddress = inetSocketAddress.getAddress().getHostAddress();
			int remoteHostPort = inetSocketAddress.getPort();
			System.out.println("[서버] 연결됨 from " + remoteHostAddress + " : " + remoteHostPort);
			
			// 5.IOStream 받아오기
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(outputStream));
			BufferedReader buf = new BufferedReader(new InputStreamReader(inputStream));
			
			String line = null;
			
			while((line = buf.readLine())!=null){
				System.out.println(line);
				pw.println(line);
				pw.flush();
			}
			pw.close();
			buf.close();
			socket.close();
		}catch(Exception ex){
			System.out.println(ex);
		}
	} 

}
