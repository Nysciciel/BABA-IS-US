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

public class Server{

	BlockingQueue<Integer> data;
	ServerCallBack callBackFunction;
	private boolean connected;
	private String ip;
	//private Level lvl;

	public Server(BlockingQueue<Integer> bq, ServerCallBack callBack) {

		this.data = bq;
		//this.lvl = level;
		this.connected = false;
		callBackFunction = callBack;

		FileInputStream fis = null;
		BufferedInputStream bis = null;
		final ServerSocket serveurSocket  ;
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
			System.out.println(ip);
			MainTest.ip_addr = ip;

			clientSocket = serveurSocket.accept();

			//try {
			DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
			fis = new FileInputStream("level.txt");
			byte[] buffer = new byte[4096];
			System.out.println("debut d'envoie de fichier");
			File file = new File("level.txt");
			int length = (int)file.length();
			System.out.println(length);
			String hex = Integer.toString(length);
			System.out.println(hex);
			hex = hex.concat("f");
			System.out.println(hex);
			buffer = hex.getBytes();
			dos.write(buffer);

			while (fis.read(buffer) > 0) {
				dos.write(buffer);
			}
			System.out.println("Fin d'envoi du fichier");
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
					while(true){
						try {
							msg = data.take();
							out.write(msg);
							out.flush();
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						};
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
						while(true) {
							in.read(b);
							callBackFunction.dataReceived(b[0]);
						}
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

	public String getIp(){return ip;}

	public void setServerCallBack(ServerCallBack cb){
		callBackFunction = cb;
	}


}
