package com.hanains.network.echo;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class UDPechoClient {
	private static String HOST_ADDRESS = "192.168.1.21";
	private static int PORT = 9090;
	private static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		DatagramSocket datagramSocket =null;
		Scanner scanner = null;
		System.out.println("exit 누르면 나갈 수 있습니다.");
		try{
			while(true){
				System.out.print("입력>>");
				scanner = new Scanner(System.in);
				String rcvData = scanner.next();
				if(rcvData.equals("exit")){
					break;
				}
				//1.UDP소켓생성
				datagramSocket = new DatagramSocket();		
				//2.전송패킷생성
				//String data = "Hello World";
				byte[] sendData = rcvData.getBytes("UTF-8");
				//바이트변경, 전송 사이즈, 주소및포트번호
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, new InetSocketAddress(HOST_ADDRESS,PORT));
				//3.데이터전송
				datagramSocket.send(sendPacket);
				//4.데이터수신
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
				datagramSocket.receive(receivePacket); //packet 받을 때 까지 Bolocking(대기)
				//5.데이터 출력
				String rcvSevData = new String(receivePacket.getData(),0,receivePacket.getLength(),"UTF-8");
				System.out.println("출력<<"+rcvSevData);
			}
		}catch(Exception e){
			log("Client Error : "+e.getMessage());
		}finally{
			scanner.close();
			if(datagramSocket != null){
				datagramSocket.close();
			}
		}
	}
	public static void log(String message){
		System.out.println("[UDP] Echo Server :"+ message);
	}
}
