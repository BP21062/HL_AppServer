package com.example.hl_appserver;


import java.util.ArrayList;
import java.util.List;

public class GameController{
	//ArrayList<Room> room_list = new ArrayList<>();
	public Room room; //紐づいてるルームのインスタンス
	public Card card1; //一枚目のカードを５枚保存する
	public Card card2; //２枚目のカードを５枚保存する
	int game_loop; //ループ回数の保存用
	public List<String> score_list = new ArrayList<>(); //スコア管理用
	public List<Integer> hit_list = new ArrayList<>(); //戦績管理用
	public List<Integer> pattern_list = new ArrayList<>(); //２０枚の絵柄管理用

	public GameController(int room_id){
		this.room = new Room(room_id);
	}

	public void startGame(){
	}

	public void displayFirstCard(){
	}

	public void enterRoom(){
	}

	public boolean checkRoomState(){
		if(room.user_count == 4){
			return false;
		}else{
			return true;
		}
	}

	public void checkConnectionState(){

	}


	public void startTimer(){
	}


	public void choiceDeckAndCardList(){

	}

	public boolean checkSuccessMessage(){
		return true;
	}

	public void countPattern(){

	}

}