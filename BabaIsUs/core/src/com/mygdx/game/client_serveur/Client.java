package com.mygdx.game.client_serveur;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.CharBuffer;
import java.rmi.server.ExportException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;






public class Client {

	BlockingQueue<Integer> data;
	ServerCallBack callBackFunction;

	public final static int FILE_SIZE = 6022386;
	private boolean connected;

	public Client(BlockingQueue<Integer> bq, ServerCallBack callBack, String ip_addr) {

		final Socket clientSocket;
		final InputStream in;
		final OutputStream out;
		this.connected = true;

		int bytesRead;
		int current = 0;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		DataInputStream dis = null;

		data = bq;
		callBackFunction = callBack;

		try {
			clientSocket = new Socket(ip_addr,5000);

			dis = new DataInputStream(clientSocket.getInputStream());
			fos = new FileOutputStream("Level/levelc.txt");
			int filesize = 0;
			byte[] buffer = new byte[4096];
			byte[] b = new byte[1];
			String hex = new String();
			try {
				while(connected) {
					dis.read(b);
					String buf = new String(b);
					if(isInteger(buf)) {
						hex = hex.concat(buf);
					}
					if(hex.contains("f")) {
						filesize = Integer.parseInt(hex.substring(0, hex.length()-1));
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				filesize =0;
			}

			int read = 0;
			int totalRead = 0;
			int remaining = filesize;
			while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
				totalRead += read;
				remaining -= read;
				fos.write(buffer, 0, read);
			}

			fos.close();
			out = clientSocket.getOutputStream();
			in = clientSocket.getInputStream();

			Thread envoyer = new Thread(new Runnable() {
				int msg;
				@Override
				public void run() {
					while(connected){
						try {
							msg = data.take();
							System.out.println("valeur envoyee "+msg);
							out.write(msg);
							out.flush();
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			envoyer.start();

			Thread recevoir= new Thread(new Runnable() {
				@Override
				public void run() {

					byte[] b = new byte[1];

					try {
						while(connected) {
							in.read(b);
							callBackFunction.dataReceived(b[0]);
							if(b[0] == 99){
								connected = false;
							}
						}
						in.close();
					} catch (IOException e) {
						try {
							in.close();
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
				}
			});
			recevoir.start();
		}catch (UnknownHostException uh) {
			connected = false;
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	public boolean isConnected(){
		return connected;
	}

	public void setConnected(boolean status){
		connected = status;
	}


	public boolean isInteger( String input ) {
		try {
			Integer.parseInt( input );
			return true;
		}
		catch( Exception e ) {
			return false;
		}
	}

}
