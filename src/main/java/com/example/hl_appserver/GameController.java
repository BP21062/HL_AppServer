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
		String R;//RoomのincreseUserCountからの返り値を保存
		Boolean WAIT;//waitMatchの返り値を保存,true(４人集まった) or false
		R = room.increaseUserCount(user_id);
		if("checkConnection".equals(R)){//4人未満
			aController.checkConnection();
			WAIT = room.waitMatch();
			while(WAIT == false){//4人未満
				aController.checkConnection();
				if(WAIT == true){//部屋に4人集まった
					break;
				}
				WAIT = room.waitMatch();
			}
			//sendMessageを呼んでgameを開始
			//aController.sendMessage();
		}else if("startGame".equals(R)){
			//他のブランチでstartGameを実装していそうだったのでマージされたら追加します。
		}
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

	public void sendMessage(String user_id, String order){
		Message message = new Message(user_id, order);
		if(order.equals("1001")){
			//通信規則に対しての操作
		}else{
		}

		aController.sendMessage(message, user_id);

	}

	public int checkRoomCount(){
		return room.user_count;
	}

}