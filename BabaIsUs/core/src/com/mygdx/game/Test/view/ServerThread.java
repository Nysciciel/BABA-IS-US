package com.mygdx.game.Test.view;

import com.mygdx.game.Level;
import com.mygdx.game.client_serveur.Server;
import com.mygdx.game.client_serveur.ServerCallBack;

import java.util.concurrent.BlockingQueue;

public class ServerThread extends Thread{
	private BlockingQueue<Integer> bq;
	ServerCallBack callBack;
	//Level level;
	private boolean clientUp;
	private Server server;
	private String serverIp;

	public ServerThread(BlockingQueue<Integer> bq, ServerCallBack callBack){
		super();
		this.server = null;
		this.serverIp = null;
		this.clientUp = false;
		this.bq = bq;
		this.callBack = callBack;
		//this.level = level;
		this.start();

	}

	public void run() {
		this.server = new Server(this.bq,this.callBack) ;
		/*do{
			serverIp = this.server.getIp();
		}while(serverIp == null);*/
	}

	public boolean checkClient(){
		if( server != null) {
			this.clientUp = true;
		}
		return clientUp;
	}

	public void setClientUp(boolean state){
		this.clientUp = state;
	}

	public Server getServer(){
		return this.server;
	}

	public String getServerIp(){
		return this.serverIp;
	}
}
