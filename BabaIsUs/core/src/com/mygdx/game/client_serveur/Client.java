package com.mygdx.game.client_serveur;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.CharBuffer;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;



public class Client {
	
	BlockingQueue<Integer> data;
	ServerCallBack callBackFunction;

	public final static int FILE_SIZE = 6022386;
	
	public Client(BlockingQueue<Integer> bq, ServerCallBack callBack, String ip_addr) {
		
		final Socket clientSocket;
		final InputStream in;
	    final OutputStream out;

		int bytesRead;
		int current = 0;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
	    
	    data = bq;
	    callBackFunction = callBack;
	    
	    try {
	         clientSocket = new Socket(ip_addr,5000);

			byte [] mybytearray  = new byte [FILE_SIZE];
			InputStream is = clientSocket.getInputStream();
			fos = new FileOutputStream("level.txt");
			bos = new BufferedOutputStream(fos);
			bytesRead = is.read(mybytearray,0,mybytearray.length);
			current = bytesRead;

			do {
				bytesRead =
						is.read(mybytearray, current, (mybytearray.length-current));
				if(bytesRead >= 0) current += bytesRead;
			} while(bytesRead > -1);

			bos.write(mybytearray, 0 , current);
			bos.flush();
			System.out.println("File " + "level.txt"
					+ " downloaded (" + current + " bytes read)");
	   
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

}
