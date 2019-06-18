package com.mygdx.game.client_serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.CharBuffer;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class Server{
	
	BlockingQueue<Integer> data;
	ServerCallBack callBackFunction;
	
	public Server(BlockingQueue<Integer> bq, ServerCallBack callBack) {
		
		this.data = bq;
		callBackFunction = callBack;
        
		 final ServerSocket serveurSocket  ;
	     final Socket clientSocket ;
	     final InputStream in;
	     final OutputStream out;
	     
	     try {
	       serveurSocket = new ServerSocket(5000);
	       clientSocket = serveurSocket.accept();
	       
	       out = clientSocket.getOutputStream();
	       in = clientSocket.getInputStream();
	       Thread envoyer = new Thread(new Runnable() {
	             int msg;
	              @Override
	              public void run() {
	                while(true){
	                  try {
						msg = data.take();
						System.out.println("msg : " + msg);
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
	            		 callBackFunction.ServerCallBack(b[0]);
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
