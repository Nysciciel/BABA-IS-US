package com.mygdx.game.client_serveur;

import com.mygdx.game.Level;
import com.mygdx.game.Test.Main.MainTest;

import java.io.*;
import java.net.*;
import java.nio.CharBuffer;
import java.util.Date;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Server{

	ConcurrentLinkedQueue<Integer> data;
	ServerCallBack callBackFunction;
	private boolean connected;
	private String ip;
	//private Level lvl;
	private ServerSocket serveurSocket  ;

	public Server(ConcurrentLinkedQueue bq, ServerCallBack callBack) {

		this.data = bq;
		//this.lvl = level;
		this.connected = false;
		callBackFunction = callBack;

		FileInputStream fis = null;
		BufferedInputStream bis = null;

		final Socket clientSocket ;
		final InputStream in;
		final OutputStream out;
		DataInputStream dis = null;
		FileOutputStream fos = null;
		ip = null;

		try {
			serveurSocket = new ServerSocket(5000);
			URL whatismyip = new URL("http://checkip.amazonaws.com");
			BufferedReader inB = new BufferedReader(new InputStreamReader(
					whatismyip.openStream()));

			this.ip = inB.readLine(); //you get the IP as a String
			MainTest.ip_addr = ip;

			clientSocket = serveurSocket.accept();

			//try {
			DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
			fis = new FileInputStream("level.txt");
			byte[] buffer = new byte[4096];
			File file = new File("level.txt");
			int length = (int)file.length();
			String hex = Integer.toString(length);
			hex = hex.concat("f");
			buffer = hex.getBytes();
			dos.write(buffer);

			while (fis.read(buffer) > 0) {
				dos.write(buffer);
			}
			/*fis.close();
				dos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			*/

			//fos.close();
			//dis.close();

			this.connected =true;
			out = clientSocket.getOutputStream();
			in = clientSocket.getInputStream();

			Thread envoyer = new Thread(new Runnable() {
				int msg;
				@Override
				public void run() {
					while(connected){
						try {
							if(data.peek()==null) {
								continue;
							}
							msg = (int) data.poll();
							System.out.println("sent;"+msg);
							System.out.println("");
							out.write(msg);
							out.flush();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					try {
						msg = 99;
						out.write(msg);
						out.flush();
						out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			envoyer.start();

			Thread recevoir= new Thread(new Runnable() {
				CharBuffer msg ;
				@Override
				public void run() {
					byte[] b = new byte[1];
					try {
						while(connected) {
							in.read(b);
							callBackFunction.dataReceived(b[0]);
							if(b[0] == 99){
								System.out.println("connection fermée par le client");
								connected = false;
							}
						}

						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			recevoir.start();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isConnected(){
		return connected;
	}

	public void setConnected(boolean status){
		connected = status;
	}

	public String getIp(){return ip;}

	public void setServerCallBack(ServerCallBack cb){
		callBackFunction = cb;
	}

	public ServerSocket getSocket(){
		return serveurSocket;
	}


}
