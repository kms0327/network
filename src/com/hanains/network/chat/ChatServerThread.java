package com.hanains.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ChatServerThread extends Thread{
	private String nickname;
	private Socket socket;
	
	List<Writer> listWriters;
	BufferedReader bufferReader = null;
	PrintWriter printWriter = null;
	
	public ChatServerThread(Socket socket, List<Writer> listWriters){
		this.socket = socket;
		this.listWriters = listWriters;
	}
	
	@Override
	public void run(){
		
		try{
			//bufferReader = 받는거 / printWriter = 클라이언튼에게 보내는거
			bufferReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),StandardCharsets.UTF_8),true);
			
			while(true){
				InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
				String remoteHostAddress = inetSocketAddress.getAddress().getHostAddress();
				int remoteHostPort = inetSocketAddress.getPort();
				
				System.out.println("[서버] 연결됨 from " + remoteHostAddress + " : " + remoteHostPort);
				
				String request = bufferReader.readLine();
				if(request == null){
					mainThread.log("클라이언트로부터 연결 끊김");
					break;
				}
				
				String[] tokens = request.split(":");
				
				if("join".equals(tokens[0])){
					doJoin(tokens[1], printWriter);
				}else if("message".equals(tokens[0])){
					doMessage(tokens[1]);
				}else if("quit".equals(tokens[0])){
					doQuit(printWriter);
				}else{
					mainThread.log("에러 알수 없는 요청("+tokens[0]+")");
				}
			}
			bufferReader.close();
			printWriter.close();
			if(socket.isClosed()==false){
				socket.close();
			}
		}catch(IOException e){
			System.out.println("ChatServerThread_1 Error : "+ e);
			doQuit(printWriter);
		}
	}
	
	private void doJoin(String nickName, Writer writer){
		this.nickname = nickName;
		
		String data = nickName+"님이 입장했습니다.\r\n";
		broadcast(data);
		
		addWriter(writer);
		
		printWriter.println("join:ok\r\n");
		printWriter.flush();
	}
	
	private void doMessage(String message){
		broadcast(nickname+": "+message+"\r\n");
	}
	private void doQuit(Writer writer){
		removeWriter(writer);
		
		String data = nickname+"님이 퇴장했습니다";
		broadcast(data);
	}
	
	private void removeWriter(Writer writer){
		synchronized(listWriters){
			listWriters.remove(writer);
		}
	}
	private void addWriter(Writer writer){
		synchronized(listWriters){
			listWriters.add(writer);
		}
	}
	
	private void broadcast(String data){
		synchronized(listWriters){
			for(Writer writer : listWriters){
				PrintWriter printWriter = (PrintWriter)writer;
				printWriter.println(data);
				printWriter.flush();
			}
		}
	}
}
