package com.example.hl_appserver;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.tyrus.server.Server;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class AController{
	public GameController game1 = new GameController(1, this);
	public GameController game2 = new GameController(2, this);
	public GameController game3 = new GameController(3, this);
	public GameController game4 = new GameController(4, this);
	public GameController game5 = new GameController(5, this);
	public GameController game6 = new GameController(6, this);
	public AServerConnector aServerConnector = new AServerConnector(this);

	public LobbyRestapiConnector lobbyRestApiConnector = new LobbyRestapiConnector(this);
	static String contextRoot = "/app";
	static String protocol = "ws";
	static int port = 8080;

	public static final String restUri = "http://localhost:8081";



	public List<String> choiceDeckAndCardList(){
		return aServerConnector.getCardList();
	}



	public void closeSession(String user_id) throws IOException{
		try{
			aServerConnector.closeSession(user_id);
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}

	public void recordResult(String user_id,int num_hit, boolean num_wins){
		aServerConnector.recordResult(user_id,num_hit,num_wins);
	}

	public boolean checkRoomState(int room_id){
		return select(room_id).checkRoomState();
	}

	public void enterRoom(int room_id, String user_id){//引数にuser_idを追加
		select(room_id).enterRoom(user_id);

	}
	public void stopUserGame(String user_id){
		if(game1.room.user_list.contains(user_id)){
			game1.stopUserGame(user_id);
		}else if(game2.room.user_list.contains(user_id)){
			game2.stopUserGame(user_id);
		}else if(game3.room.user_list.contains(user_id)){
			game3.stopUserGame(user_id);
		}else if(game4.room.user_list.contains(user_id)){
			game4.stopUserGame(user_id);
		}else if(game5.room.user_list.contains(user_id)){
			game5.stopUserGame(user_id);
		}else if(game6.room.user_list.contains(user_id)){
			game6.stopUserGame(user_id);
		}
	}




	public void calculateScore(int room_id,String user_id, String choice, String pattern){
		select(room_id).calculateScore(user_id, choice, pattern);
	}

	public GameController select(int room_id){//room_idの判定用、if文重複を避ける為
		if(room_id == 1){
			return game1;
		}else if(room_id == 2){
			return game2;
		}else if(room_id == 3){
			return game3;
		}else if(room_id == 4){
			return game4;
		}else if(room_id == 5){
			return game5;
		}else{
			return game6;
		}
	}


	public void checkSuccessMessage(int room_id, String order) throws IOException{
		try{
			select(room_id).checkSuccessMessage(order);
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}

	public void memorizeUser(String user_id){
		aServerConnector.memorizeUser(user_id);
	}

	public void sendMessage(Message message, String user_id){
		aServerConnector.sendMessage(AServerConnector.reverse_user_map.get(user_id), message);

	}

	/**
	 * checkRoomCountメソッド
	 * ルームの在室人数を返す
	 *
	 * @return List<Integer>
	 */
	public List<Integer> checkRoomCount(){
		List<Integer> user_count = new ArrayList<>();
		user_count.add(game1.checkRoomCount());
		user_count.add(game2.checkRoomCount());
		user_count.add(game3.checkRoomCount());
		user_count.add(game4.checkRoomCount());
		user_count.add(game5.checkRoomCount());
		user_count.add(game6.checkRoomCount());
		return user_count;
	}


	public static void main(String[] args) throws Exception{
		Server server = new Server(protocol, port, contextRoot, null, AServerConnector.class);
		final ResourceConfig rc = new ResourceConfig().packages("");
		final HttpServer restServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(restUri), rc);

		try{
			server.start();
			System.in.read();
		}finally{
			server.stop();
			restServer.shutdownNow();
		}
	}


}
