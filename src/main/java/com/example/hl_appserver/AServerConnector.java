package com.example.hl_appserver;

import com.google.gson.Gson;
import sample.EndpointExample;
import sample.EndpointSample;
import sample.SampleMessage;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/")
public class AServerConnector{

	public  AController aController;

	private static Set<Session> establishedSessions
			= Collections.synchronizedSet(new HashSet<Session>());

	static Gson gson = new Gson();

	@OnOpen
	public void onOpen(Session session, EndpointConfig ec) {
		//セッションを記憶
		boolean room1;
		establishedSessions.add(session);
		//ログに出力
		System.out.println("[WebSocketServerSample] onOpen:" + session.getId());
	}

	@OnMessage
	public void onMessage(final String message, final Session session) throws IOException{
		System.out.println("[WebSocketServerExample] onMessage from (session: " + session.getId() + ") msg: " + message);
		// 変換：String -> SampleMessage
		Message receivedMessage = gson.fromJson(message, Message.class);
		//通信規則ごとの立ち回りを記述
		if(receivedMessage.order.equals("---")){

		}
	}

	@OnClose
	public void onClose(Session session){
		System.out.println("[WebSocketServerExample] onClose:" + session.getId());
		establishedSessions.remove(session);

		//logout関数
	}

	@OnError
	public void onError(Session session, Throwable error){
		System.out.println("[WebSocketServerExample] onError:" + session.getId());
	}


	public Message sendMessage(Message message){
		return message;
	}

	public void connect(String server_name){}

	public void enterRoom(int room_id){}

	public void checkRoomState(){}

	public void checkConnection(){}

	public void logout(String user_id){}

	public void getRule(){}

	public void getScore(){}

	public void getCardList(){}

	public void getErrorInfo(){}
}
