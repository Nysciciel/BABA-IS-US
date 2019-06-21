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
	     
	     try {
	       serveurSocket = new ServerSocket(5000);
	       clientSocket = serveurSocket.accept();
	       this.connected =true;

			 File myFile = new File ("Level.txt");
			 byte [] mybytearray  = new byte [(int)myFile.length()];
			 fis = new FileInputStream(myFile);
			 bis = new BufferedInputStream(fis);
			 bis.read(mybytearray,0,mybytearray.length);
			 out = clientSocket.getOutputStream();
			 System.out.println("Sending " + " Level.txt " + "(" + mybytearray.length + " bytes)");
			 out.write(mybytearray,0,mybytearray.length);
			 out.flush();

	        //out = clientSocket.getOutputStream();
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
	     finally {
			 /*if (bis != null) bis.close();
			 if (out != null) out.close();
			 if (clientSocket!=null) clientSocket.close();*/
		 }
	     
	}

	public boolean isConnected(){
		return connected;
	}

	public void setServerCallBack(ServerCallBack cb){
		callBackFunction = cb;
	}


}
