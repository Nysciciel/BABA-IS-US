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
}
