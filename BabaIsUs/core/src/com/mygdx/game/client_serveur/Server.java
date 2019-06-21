package com.mygdx.game.client_serveur;

import com.mygdx.game.Level;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.CharBuffer;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class Server{
	
	BlockingQueue<Integer> data;
	ServerCallBack callBackFunction;
	private boolean connected;
	private Level lvl;
	
	public Server(BlockingQueue<Integer> bq, ServerCallBack callBack,Level level) {
		
		this.data = bq;
		this.lvl = level;
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
	     
	     try {
	       serveurSocket = new ServerSocket(5000);
	       clientSocket = serveurSocket.accept();

			 dis = new DataInputStream(clientSocket.getInputStream());
			 fos = new FileOutputStream("level.txt");
			 byte[] buffer = new byte[4096];

			 int filesize = 15123; // Send file size in separate msg
			 int read = 0;
			 int totalRead = 0;
			 int remaining = filesize;
			 System.out.println("debut d'enregistrement de fichier");
			 while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
				 totalRead += read;
				 remaining -= read;
				 System.out.println("read " + totalRead + " bytes.");
				 fos.write(buffer, 0, read);
			 }
			 System.out.println("fin d'enregistrement de fichier");
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
	      }finally {
	     	try {
				fos.close();
				dis.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		 }
	}

	public boolean isConnected(){
		return connected;
	}

	public void setServerCallBack(ServerCallBack cb){
		callBackFunction = cb;
	}


}
