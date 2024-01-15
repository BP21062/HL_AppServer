package com.example.hl_appserver;

import com.google.gson.Gson;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.*;

@ServerEndpoint("/playgame")
public class AServerConnector{

	// REST APIは送ってきたけど、まだWebSocketに接続してないユーザーのリスト
	public static List<String> memo_user_list = new ArrayList<>();

	private static Set<Session> establishedSessions = Collections.synchronizedSet(new HashSet<Session>());
	
	// Sessionとuser_idの紐づけ
	public static Map<Session, String> user_map = new HashMap<>();

	// 上の逆
	public static Map<String, Session> reverse_user_map = new HashMap<>();

	static Gson gson = new Gson();

	@OnOpen
	public void onOpen(Session session, EndpointConfig ec) {
		// セッションを記憶
		establishedSessions.add(session);
		// ログに出力
		System.out.println("[App] onOpen:" + session.getId());
	}

	@OnMessage
	public void onMessage(final String message, final Session session) throws IOException {
		System.out.println("[App] onMessage from (session: " + session.getId() + ") msg: " + message);
		// 変換：String -> SampleMessage
		Message receivedMessage = gson.fromJson(message, Message.class);
		//通信規則ごとの立ち回りを記述
		if(receivedMessage.order.equals("1999")){
			if(memo_user_list.contains(receivedMessage.messageContent.user_id)){
				memo_user_list.remove(receivedMessage.messageContent.user_id);
				user_map.put(session, receivedMessage.messageContent.user_id);
				reverse_user_map.put(receivedMessage.messageContent.user_id, session);
			}else session.close();
			//1004 画面遷移メッセージを受信
		}else if(receivedMessage.order.equals("1004")){
			if(receivedMessage.result){
				AController.checkSuccessMessage(receivedMessage.messageContent.room_id, receivedMessage.order);
			}
		}else if(receivedMessage.order.equals("1005")){
			if(receivedMessage.result){
				AController.checkSuccessMessage(receivedMessage.messageContent.room_id, receivedMessage.order);
			}
		}else if(receivedMessage.order.equals("1006")){
			if(receivedMessage.result){
				AController.checkSuccessMessage(receivedMessage.messageContent.room_id, receivedMessage.order);
			}
		}else if(receivedMessage.order.equals("1007")){
			AController.calculateScore(receivedMessage.messageContent.room_id, receivedMessage.messageContent.user_id, receivedMessage.messageContent.choice, receivedMessage.messageContent.pattern);
		}else if(receivedMessage.order.equals("1008")){
			if(receivedMessage.result){
				AController.checkSuccessMessage(receivedMessage.messageContent.room_id, receivedMessage.order);
			}
		}else if(receivedMessage.order.equals("1003")){
			//enterRoom
			user_map.put(session,receivedMessage.messageContent.user_id);
			reverse_user_map.put(receivedMessage.messageContent.user_id,session);
			AController.enterRoom(receivedMessage.messageContent.room_id, receivedMessage.messageContent.user_id);
		}else if(receivedMessage.order.equals("test")){
			System.out.println("成功!!!!");
			Message message77777 = new Message("ok", "ok");
			sendMessage(session, message77777);

		}
	}

	@OnClose
	public void onClose(Session session) {
		System.out.println("[App] onClose:" + session.getId());
		establishedSessions.remove(session);
		reverse_user_map.remove(user_map.get(session));
		AController.stopUserGame(user_map.get(session));
		user_map.remove(session);
	}

	@OnError
	public void onError(Session session, Throwable error){
		System.out.println("[App] onError:" + session.getId());
		System.out.println("[App] Cause by" + error.getMessage());
		try{
			AController.stopUserGame(user_map.get(session));
			session.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void sendMessage(Session session, Message message){
		String send_message = gson.toJson(message);
		try {
			// 同期送信（sync）
			session.getBasicRemote().sendText(send_message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	public static void memorizeUser(String user_id){
		memo_user_list.add(user_id);
	}




	public static void closeSession(String user_id) throws IOException{
		try{
			reverse_user_map.get(user_id).close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static List<String> getCardList(){
		List<String> card_list;
		card_list = DatabaseConnector.getCardList();
		return card_list;
	}
	public static void recordResult(String user_id, int num_hit, boolean num_wins){
		DatabaseConnector.saveScore(user_id,num_hit,num_wins);
	}

}
