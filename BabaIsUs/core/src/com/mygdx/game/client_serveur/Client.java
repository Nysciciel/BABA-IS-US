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

			//try {
				DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
				FileInputStream fis = new FileInputStream("level.txt");
				byte[] buffer = new byte[4096];
			System.out.println("debut d'envoie de fichier");
				while (fis.read(buffer) > 0) {
					dos.write(buffer);
				}
			System.out.println("Fin d'envoi du fichier");
				/*fis.close();
				dos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}*/

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
