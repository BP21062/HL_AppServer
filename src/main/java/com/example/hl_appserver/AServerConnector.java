package com.example.hl_appserver;

import com.google.gson.Gson;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

@ServerEndpoint("/")
public class AServerConnector{
	public DatabaseConnector databaseConnector;


	public AController aController;

	private static Set<Session> establishedSessions = Collections.synchronizedSet(new HashSet<Session>());

	static Gson gson = new Gson();

	public AServerConnector(AController aController){
		this.aController = aController;
	}

	@OnOpen
	public void onOpen(Session session, EndpointConfig ec){
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
		if(receivedMessage.order.equals("1000")){
			List<Integer> scoreList;
			Message message5000 = new Message("5000", receivedMessage.messageContent.user_id);
			scoreList = getScore(receivedMessage.messageContent.user_id);
			message5000.messageContent.num_plays_score = scoreList.get(0);
			message5000.messageContent.num_wins_score = scoreList.get(1);
			message5000.messageContent.num_hits_score = scoreList.get(2);
			sendMessage(session, message5000);
		}else if(receivedMessage.order.equals("1001")){
			Message message5001 = new Message("5001", receivedMessage.messageContent.user_id);
			message5001.messageContent.textDataList.add(getRule());
			sendMessage(session, message5001);
		}else{
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


	public void sendMessage(Session session, Message message){
		//System.out.println("[WebSocketServerExample] sendMessage(): " + message);
		String send_message = gson.toJson(message);
		try{
			// 同期送信（sync）
			session.getBasicRemote().sendText(send_message);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void connect(String server_name){
	}

	public void enterRoom(int room_id){
	}

	public void checkRoomState(){
	}

	public void checkConnection(){
	}

	public void logout(String user_id){
	}

	public String getRule(){
		String rule;
		rule = databaseConnector.getRule();
		return rule;
	}

	public List<Integer> getScore(String user_id){
		List<Integer> scoreList;
		scoreList = databaseConnector.getScore(user_id);
		return scoreList;
	}

	public void getCardList(){
	}

	public void getErrorInfo(){
	}
}
