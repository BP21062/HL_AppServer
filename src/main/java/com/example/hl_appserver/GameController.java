package com.example.hl_appserver;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameController{
	public Room room; //紐づいてるルームのインスタンス
	public Card card1; //一枚目のカードを５枚保存する
	public Card card2; //２枚目のカードを５枚保存する
	int check_success_message = 0; //クライアントの遷移確認用

	int game_loop = 0; //ループ回数の保存用
	public List<String> score_list = new ArrayList<>(); //スコア管理用
	public List<Integer> hit_list = new ArrayList<>(); //戦績管理用
	public List<Integer> pattern_list = new ArrayList<>(); //２０枚の絵柄管理用


	public AController aController;

	public GameController(int room_id, AController aController){
		this.aController = aController;
		this.room = new Room(room_id);
	}

	public void startGame(){
		//game_loopを1増やす
		game_loop++;
		if(game_loop == 1){
			//カードを山から選択して保存
			choiceDeckAndCardList();
		}
		//現在のスコアを表示
		displayCurrentPoint();

	}
	public void displayCurrentPoint(){
		for(String user : room.user_list){
			sendMessage(user, "5003");
		}
	}

	public void displayFirstCard(){
		for(String user : room.user_list){
			sendMessage(user, "5004");
		}
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

	public void checkSuccessMessage(String order){
		if(order.equals("1004")){
			check_success_message++;
			if(check_success_message == 4){
				check_success_message = 0;
				startGame();

			}
		}else if(order.equals("1005")){
			check_success_message++;
			if(check_success_message == 4){
				check_success_message = 0;
				//１枚目を表示するメッセージを送信
				displayFirstCard();


			}
		}else if(order.equals("1006")){
			check_success_message++;
			if(check_success_message == 4){
				check_success_message = 0;
				//タイマーをスタート
				startTimer();

			}
		}
	}


	public void startTimer(){//30秒のカウントダウン？
		int REMAINING_TIME = 30;//残り時間

		for(int i = REMAINING_TIME; i > 0; i--){
			REMAINING_TIME = i;

			try{
				Thread.sleep(1000);//1秒
			}catch(InterruptedException e){
				e.printStackTrace();
			}

		}
		//sendMessage　→ score 2枚目

	}

	public void calculateScore(){

	}

	public void choiceDeckAndCardList(){
		//５２枚ぶっこみ用
		List<String> all_card_list = new ArrayList<>();
		all_card_list = aController.choiceDeckAndCardList();

		List<Integer> numbers = new ArrayList<>();
		for(int i = 0; i <= 51; i++){
			numbers.add(i);
		}

		// 20個の数字をランダムに選ぶ
		Collections.shuffle(numbers);
		List<Integer> selected20 = numbers.subList(0, 20);

		// 20個の数字から5個選ぶ
		Collections.shuffle(selected20);
		List<Integer> selected_5_from_20 = selected20.subList(0, 5);

		// 選ばれなかった数字を残りの32個に入れる
		List<Integer> remaining_numbers = new ArrayList<>();
		for(int i = 0; i <= 51; i++){
			if(!selected20.contains(i)){
				remaining_numbers.add(i);
			}
		}

		// 残りの32個の数字からさらに5個選ぶ
		Collections.shuffle(remaining_numbers);
		List<Integer> selected5_from_remaining = remaining_numbers.subList(0, 5);

		//1枚目を保存
		for(Integer num : selected5_from_remaining){
			card1.saveCard(all_card_list.get(num), num+1);
		}

		//２枚目を保存
		for(Integer num : selected_5_from_20){
			card2.saveCard(all_card_list.get(num), num+1);
		}

		pattern_list.set(0, 0);//spade
		pattern_list.set(1, 0);//club
		pattern_list.set(2, 0);//dia
		pattern_list.set(3, 0);//heart

		//pattern_list更新用
		int current_point;

		//絵柄を集計
		for(Integer num : selected20){
			if(checkCardPattern(num + 1) == 0){
				current_point = pattern_list.get(0);
				pattern_list.set(0, current_point + 1);
			}else if(checkCardPattern(num + 1) == 1){
				current_point = pattern_list.get(1);
				pattern_list.set(1, current_point + 1);
			}else if(checkCardPattern(num + 1) == 2){
				current_point = pattern_list.get(2);
				pattern_list.set(2, current_point + 1);
			}else if(checkCardPattern(num + 1) == 3){
				current_point = pattern_list.get(3);
				pattern_list.set(3, current_point + 1);
			}

		}


	}

	public int checkCardPattern(int num){
		return (int) (num / 13);
	}


	public void countPattern(){

	}

	public void sendMessage(String user_id, String order){
		Message message = new Message(user_id, order);
		if(order.equals("5003")){
			message.messageContent.room_id = room.room_id;
			message.messageContent.user_list = room.user_list;
			message.messageContent.score_list = room.score_list;
		}else if(order.equals("5004")){
			message.messageContent.room_id = room.room_id;
			message.messageContent.pattern_list = pattern_list;
			message.messageContent.image_data = card1.image.get(0);
		}

		aController.sendMessage(message, user_id);

	}

	public int checkRoomCount(){
		return room.user_count;
	}


}
