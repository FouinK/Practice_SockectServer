package com.example.WebSocketServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

@SpringBootApplication
public class WebSocketServerApplication {

	public static void main(String[] args) {

		Socket socket = null;                //클라이언트와 통신을 위한 소켓
		ServerSocket server_socket = null;  // 서버생성 서버소켓
		BufferedReader in = null;            //클라이언트 데이터 읽기 입력스트림
		PrintWriter out = null;                //클라이언트에 데이터 전송 출력스트림
		try {
			Scanner scanner = new Scanner(System.in);
			System.out.print("사용할 포트 번호를 입력해주세요 (ex.8080)");
			int port = scanner.nextInt();
			System.out.println();
			System.out.println("클라이언트 서버에서 해당 서버의 ip주소로 접속하면 소켓 통신이 시작됩니다.");
			server_socket = new ServerSocket(port);
			socket = server_socket.accept();    //서버 생성(클라이언트 접송 대기)
		} catch (IOException e) {
			System.out.println("포트가 이미 사용중.");
		}
		while (true) {
			System.out.println("서버가 열렸습니다. 클라이언트의 ping 메세지는 pong으로 응답합니다.");
			try {
				System.out.println("클라이언트의 메세지를 기다리는 중 ...");
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));    //입력스트림 생성
				out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))); //출력스트림 생성

				String str = null;
				str = in.readLine();                //클라이언트의 데이터 읽기

				System.out.println("Client의 메세지 : " + str);
				if (str.equals("ping")) {
					out.write("pong\n");
					out.flush();
					System.out.println("pong < 클라이언트로 응답 완료");
				} else {
					out.write(str + "\n");
					out.flush();
					System.out.println(str+" < 클라이언트로 응답 완료");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
