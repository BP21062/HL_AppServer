package com.example.hl_appserver;


import java.util.ArrayList;
import java.util.List;

public class GameController{
	public Room room; //紐づいてるルームのインスタンス
	public Card card1; //一枚目のカードを５枚保存する
	public Card card2; //２枚目のカードを５枚保存する
	int game_loop; //ループ回数の保存用
	public List<String> score_list = new ArrayList<>(); //スコア管理用
	public List<Integer> hit_list = new ArrayList<>(); //戦績管理用
	public List<Integer> pattern_list = new ArrayList<>(); //２０枚の絵柄管理用

	public AController aController;

	public GameController(int room_id, AController aController){
		this.aController = aController;
		this.room = new Room(room_id);
	}

	public void startGame(){
		game_loop = 0;
	}

	public void displayFirstCard(){
	}

	public void enterRoom(String user_id){//引数をroom_idからuser_idに変更
		room.increaseUserCount(user_id);
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


	public void startTimer(){//20秒のカウントダウン？
		int REMAINING_TIME = 20;//残り時間

		for(int i = REMAINING_TIME; i > 0; i--){
			REMAINING_TIME = i;

			try{
				Thread.sleep(1000);//1秒
			}catch(InterruptedException e){
				e.printStackTrace();
			}

		}

	}

	public void calculateScore(){

	}

	public void choiceDeckAndCardList(){

	}

	public boolean checkSuccessMessage(){
		return true;
	}

	public void countPattern(){

	}


}