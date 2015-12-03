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

public class chatClientReceiveThread extends Thread {
	BufferedReader bufferReader = null;
	
	public chatClientReceiveThread(BufferedReader bufferReader) {
		this.bufferReader = bufferReader;
	}
	@Override
	public void run(){
		try {
			while(true){
				String me = bufferReader.readLine();
				
				if(me == null){
					break;
				}
				System.out.println(me);
			}
		} catch (IOException e) {
			System.out.println("Client Error");
		} 
	}
}
