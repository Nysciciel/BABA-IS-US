package com.mygdx.game.Test.view;

import com.mygdx.game.Level;
import com.mygdx.game.client_serveur.Server;
import com.mygdx.game.client_serveur.ServerCallBack;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServerThread extends Thread{
	private ConcurrentLinkedQueue bq;
	ServerCallBack callBack;
	//Level level;
	private boolean clientUp;
	private Server server;
	private String serverIp;
	private String filename;

	public ServerThread(ConcurrentLinkedQueue bq, ServerCallBack callBack, String filename){
		super();
		this.server = null;
		this.serverIp = null;
		this.clientUp = false;
		this.bq = bq;
		this.callBack = callBack;
		this.filename = filename;
		//this.level = level;
		this.start();

	}

	public void run() {
		this.server = new Server(this.bq,this.callBack, this.filename) ;
		/*do{
			serverIp = this.server.getIp();
		}while(serverIp == null);*/
	}

	public boolean checkClient(){
		if( server != null && server.isConnected()) {
			this.clientUp = true;
		}else{
			this.clientUp = false;
		}
		return clientUp;
	}

	public void setConnection(boolean state){
		this.server.setConnected(state);
	}

	public Server getServer(){
		return this.server;
	}

	public String getServerIp(){
		return this.serverIp;
	}

	public void shutCO(){
		try {
			server.getSocket().close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void getStatus(){
		System.out.println("this Thread state : " + this.getState());
		this.server.getThreadStatus();
	}
}
