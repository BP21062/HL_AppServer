package com.example.hl_appserver;

import com.google.gson.Gson;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

@ServerEndpoint("/")
public class AServerConnector{
	public List<String> memo_user_list = new ArrayList<>();
	public DatabaseConnector databaseConnector;


	public AController aController;

	private static Set<Session> establishedSessions = Collections.synchronizedSet(new HashSet<Session>());
	public static Map<Session,String> user_map = new HashMap<>();
	public static Map<String,Session> reverse_user_map = new HashMap<>();

	static Gson gson = new Gson();


	public AServerConnector(AController aController){
		this.aController = aController;
	}

	@OnOpen
	public void onOpen(Session session, EndpointConfig ec){
		//セッションを記憶
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
			message5001.messageContent.image_data = getRule();
			sendMessage(session, message5001);
		}else if(receivedMessage.order.equals("1999")){
			if(memo_user_list.contains(receivedMessage.messageContent.user_id)){
				memo_user_list.remove(receivedMessage.messageContent.user_id);
				user_map.put(session, receivedMessage.messageContent.user_id);
				reverse_user_map.put(receivedMessage.messageContent.user_id, session);
			}else session.close();
			//1004 画面遷移メッセージを受信
		}else if(receivedMessage.order.equals("1004")){
			if(receivedMessage.result){
				aController.checkSuccessMessage(receivedMessage.messageContent.room_id, receivedMessage.order);
			}
		}else if(receivedMessage.order.equals("1005")){
			if(receivedMessage.result){
				aController.checkSuccessMessage(receivedMessage.messageContent.room_id, receivedMessage.order);
			}
		}else if(receivedMessage.order.equals("1006")){
			if(receivedMessage.result){
				aController.checkSuccessMessage(receivedMessage.messageContent.room_id, receivedMessage.order);
			}
		}else if(receivedMessage.order.equals("1007")){
			aController.calculateScore(receivedMessage.messageContent.room_id, receivedMessage.messageContent.user_id, receivedMessage.messageContent.choice, receivedMessage.messageContent.pattern);
		}else if(receivedMessage.order.equals("1008")){
			if(receivedMessage.result){
				aController.checkSuccessMessage(receivedMessage.messageContent.room_id, receivedMessage.order);
			}
		}else if(receivedMessage.order.equals("1003")){
			aController.enterRoom(receivedMessage.messageContent.room_id, receivedMessage.messageContent.user_id);
		}
	}

	@OnClose
	public void onClose(Session session){
		System.out.println("[WebSocketServerExample] onClose:" + session.getId());
		establishedSessions.remove(session);
		user_map.remove(session);
		reverse_user_map.remove(user_map.get(session));
	}

	@OnError
	public void onError(Session session, Throwable error){
		System.out.println("[WebSocketServerExample] onError:" + session.getId());
		try{
			session.close();
			aController.stopUserGame(user_map.get(session));

		}catch(IOException e){
			e.printStackTrace();
		}

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

	public void memorizeUser(String user_id){
		memo_user_list.add(user_id);
	}

	public void enterRoom(int room_id, String user_id){//引数にuser_idを追加
		aController.enterRoom(room_id, user_id);
	}

	public boolean checkRoomState(int room_id){
		return aController.checkRoomState(room_id);
	}



	public void closeSession(String user_id) throws IOException{
		try{
			reverse_user_map.get(user_id).close();
		}catch(IOException e){
			throw new RuntimeException(e);
		}
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

	public List<String> getCardList(){
		List<String> card_list = new ArrayList<>();
		card_list = databaseConnector.getCardList();
		return card_list;
	}
	public void recordResult(String user_id,int num_hit, boolean num_wins){
		databaseConnector.saveScore(user_id,num_hit,num_wins);
	}

	public void getErrorInfo(){
	}
}
