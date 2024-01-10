package com.example.hl_appserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AControllerContents{

	public GameController game1 = GameControllerContent.game1;
	public GameController game2 = GameControllerContent.game2;
	public GameController game3 = GameControllerContent.game3;
	public GameController game4 = GameControllerContent.game4;
	public GameController game5 = GameControllerContent.game5;
	public GameController game6 = GameControllerContent.game6;
	public AServerConnector aServerConnector = GameControllerContent.aServerConnector;

	public boolean checkRoomState(int room_id){
		return select(room_id).checkRoomState();
	}

	public void enterRoom(int room_id, String user_id){//引数にuser_idを追加
		if(room_id == 1){
			if(Objects.isNull(game1)){
				GameControllerContent gameControllerContent = new GameControllerContent();
				gameControllerContent.generateGC(room_id);
				this.game1 = GameControllerContent.game1;
			}
			game1.enterRoom(user_id);
			System.out.println(game1.room.user_count);
			System.out.println(game1);
			prepareStartGame(game1.room.user_count);
		}else if(room_id == 2){
			if(Objects.isNull(game2)){
				GameControllerContent gameControllerContent = new GameControllerContent();
				gameControllerContent.generateGC(room_id);
				this.game2 = GameControllerContent.game2;
			}
			game2.enterRoom(user_id);
			System.out.println(game2.room.user_count);
			System.out.println(game2);
		}else if(room_id == 3){
			if(Objects.isNull(game3)){
				GameControllerContent gameControllerContent = new GameControllerContent();
				gameControllerContent.generateGC(room_id);
				this.game3 = GameControllerContent.game3;
			}
			game3.enterRoom(user_id);
			System.out.println(game3.room.user_count);
			System.out.println(game3);
		}else if(room_id == 4){
			if(Objects.isNull(game4)){
				GameControllerContent gameControllerContent = new GameControllerContent();
				gameControllerContent.generateGC(room_id);
				this.game4 = GameControllerContent.game4;
			}
			game4.enterRoom(user_id);
			System.out.println(game4.room.user_count);
			System.out.println(game4);
		}else if(room_id == 5){
			if(Objects.isNull(game5)){
				GameControllerContent gameControllerContent = new GameControllerContent();
				gameControllerContent.generateGC(room_id);
				this.game5 = GameControllerContent.game5;
			}
			game5.enterRoom(user_id);
			System.out.println(game5.room.user_count);
			System.out.println(game5);
		}else if(room_id == 6){
			if(Objects.isNull(game6)){
				GameControllerContent gameControllerContent = new GameControllerContent();
				gameControllerContent.generateGC(room_id);
				this.game6 = GameControllerContent.game6;
			}
			game6.enterRoom(user_id);
			System.out.println(game6.room.user_count);
			System.out.println(game6);
		}else{
			//エラー処理
			System.out.println("Unko");
		}
	}

	//開始画面画面切り替えのためのsendMessageメソッド
	public void prepareStartGame(int member){
		if(member == 4){
			AServerConnector aServerConnector = new AServerConnector();
			for(int i=0;i<4;i++){
				System.out.println(AServerConnector.reverse_user_map.get(GameControllerContent.game1.room.user_list.get(i)));
				Message message = new Message("5002",GameControllerContent.game1.room.user_list.get(i));
				aServerConnector.sendMessage(AServerConnector.reverse_user_map.get(GameControllerContent.game1.room.user_list.get(i)),message);
			}
		}

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

	public void sendMessage(Message message, String user_id){

		if(Objects.isNull(aServerConnector)){
			GameControllerContent gameControllerContent = new GameControllerContent();
			gameControllerContent.generateASC();
			this.aServerConnector = GameControllerContent.aServerConnector;
		}

		aServerConnector.sendMessage(AServerConnector.reverse_user_map.get(user_id),message);

	}

	public void recordResult(String user_id, int hits, boolean win){
		aServerConnector.recordResult(user_id,hits,win);
	}

	public void closeSession(String user_id){
		try{
			aServerConnector.closeSession(user_id);
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}

	public List<String> choiceDeckAndCardList(){
		return aServerConnector.getCardList();
	}
	public void memorizeUser(String user_id){
		aServerConnector.memorizeUser(user_id);
	}
}
