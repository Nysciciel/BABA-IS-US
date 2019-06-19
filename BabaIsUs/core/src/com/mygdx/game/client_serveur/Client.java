package com.mygdx.game.client_serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.CharBuffer;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class Client {
	
	BlockingQueue<Integer> data;
	ServerCallBack callBackFunction;
	
	public Client(BlockingQueue<Integer> bq, ServerCallBack callBack, String ip_addr) {
		
		final Socket clientSocket;
		final InputStream in;
	    final OutputStream out;
	    
	    data = bq;
	    callBackFunction = callBack;
	    
	    try {
	         clientSocket = new Socket(ip_addr,5000);
	   
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
