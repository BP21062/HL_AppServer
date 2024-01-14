package com.example.hl_appserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AController{

	static GameController game1;
	static GameController game2;
	static GameController game3;
	static GameController game4;
	static GameController game5;
	static GameController game6;

	/**
	 * checkRoomCountメソッド
	 * ルームに入れるかどうかを返す
	 * @return room_id
	 */
	public static boolean checkRoomState(int room_id){
		if(room_id == 1){
			if(Objects.isNull(game1)){
				game1 = new GameController(1);
			}
		}else if(room_id == 2){
			if(Objects.isNull(game2)){
				game2 = new GameController(1);
			}

		}else if(room_id == 3){
			if(Objects.isNull(game3)){
				game3 = new GameController(1);
			}

		}else if(room_id == 4){
			if(Objects.isNull(game4)){
				game4 = new GameController(1);
			}

		}else if(room_id == 5){
			if(Objects.isNull(game5)){
				game5 = new GameController(1);
			}

		}else if(room_id == 6){
			if(Objects.isNull(game6)){
				game6 = new GameController(1);
			}

		}else{
			//エラー処理
			System.out.println("error");
		}

		return select(room_id).checkRoomState();
	}

	public static void enterRoom(int room_id, String user_id){//引数にuser_idを追加
		if(room_id == 1){
			game1.enterRoom(user_id);
			prepareStartGame(game1);
		}else if(room_id == 2){
			game2.enterRoom(user_id);
			prepareStartGame(game2);
		}else if(room_id == 3){
			game3.enterRoom(user_id);
			prepareStartGame(game3);
		}else if(room_id == 4){
			game4.enterRoom(user_id);
			prepareStartGame(game4);
		}else if(room_id == 5){
			game5.enterRoom(user_id);
			prepareStartGame(game5);
		}else if(room_id == 6){
			game6.enterRoom(user_id);
			prepareStartGame(game6);
		}else{
			//エラー処理
			System.out.println("Error");
		}
		System.out.println("[App] enterRoom:" + user_id);
	}

	//開始画面画面切り替えのためのsendMessageメソッド
	//回収予定
	public static void prepareStartGame(GameController game){
		if(game.room.user_count == 4){
			try{
				Thread.sleep(5000);
			}catch(InterruptedException e){
				throw new RuntimeException(e);
			}
			for(int i=0;i<4;i++){
				System.out.println("[App] prepareStartGame: " +AServerConnector.reverse_user_map.get(game.room.user_list.get(i)).toString());
				Message message = new Message("5002",game.room.user_list.get(i));
				AServerConnector.sendMessage(AServerConnector.reverse_user_map.get(game.room.user_list.get(i)),message);
			}
		}

	}

	public static void stopUserGame(String user_id){
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




	public static void calculateScore(int room_id,String user_id, String choice, String pattern){
		select(room_id).calculateScore(user_id, choice, pattern);
	}

	public static GameController select(int room_id){//room_idの判定用、if文重複を避ける為
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


	public static void checkSuccessMessage(int room_id, String order) throws IOException{
		try{
			select(room_id).checkSuccessMessage(order);
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}


	/**
	 * checkRoomCountメソッド
	 * ルームの在室人数を返す
	 *
	 * @return List<Integer>
	 */
	public static List<Integer> checkRoomCount(){
		List<Integer> user_count = new ArrayList<>();
		user_count.add(game1.checkRoomCount());
		user_count.add(game2.checkRoomCount());
		user_count.add(game3.checkRoomCount());
		user_count.add(game4.checkRoomCount());
		user_count.add(game5.checkRoomCount());
		user_count.add(game6.checkRoomCount());
		return user_count;
	}

	public static void sendMessage(Message message, String user_id){
		AServerConnector.sendMessage(AServerConnector.reverse_user_map.get(user_id),message);
	}

	public static void recordResult(String user_id, int hits, boolean win){
		AServerConnector.recordResult(user_id,hits,win);
	}

	public static void closeSession(String user_id){
		try{
			AServerConnector.closeSession(user_id);
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}

	public static List<String> choiceDeckAndCardList(){
		return AServerConnector.getCardList();
	}
	public static void memorizeUser(String user_id){
		AServerConnector.memorizeUser(user_id);
	}
}
