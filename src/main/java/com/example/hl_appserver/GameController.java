package com.example.hl_appserver;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameController{
	public Room room; //紐づいてるルームのインスタンス
	public Card card1; //一枚目のカードを５枚保存する
	public Card card2; //２枚目のカードを５枚保存する
	int check_success_message = 0; //クライアントの遷移確認用

	int game_loop = 0; //ループ回数の保存用
	public List<Integer> pattern_list = new ArrayList<>(); //２０枚の絵柄管理用


	public AController aController;

	public GameController(int room_id){
		GameControllerContent gameControllerContent = new GameControllerContent();
		gameControllerContent.generateRoom(room_id);
		if(room_id == 1){
			this.room = GameControllerContent.room1;
		}else if(room_id == 2){
			this.room = GameControllerContent.room2;
		}else if(room_id == 3){
			this.room = GameControllerContent.room3;
		}else if(room_id == 4){
			this.room = GameControllerContent.room4;
		}else if(room_id == 5){
			this.room = GameControllerContent.room5;
		}else if(room_id == 6){
			this.room = GameControllerContent.room6;
		}
	}

	public void startGame() throws IOException{
		//game_loopを1増やす
		game_loop++;
		if(game_loop == 1){
			//カードを山から選択して保存
			choiceDeckAndCardList();
			//現在のスコアを表示
			displayCurrentPoint();
		}else if(game_loop == 6 || room.user_count == 1){
			try{
				endGame();
			}catch(IOException e){
				throw new RuntimeException(e);
			}
		}else{
			//現在のスコアを表示
			displayCurrentPoint();
		}

	}
	public void endGame() throws IOException{
		//最終スコアの送信
		for(String user : room.user_list){
			sendMessage(user, "5006");
		}
		//戦績の保存
		for (int i = 0; i < room.user_list.size(); i++) {
			//aController.recordResult(room.user_list.get(i), room.hit_list.get(i), checkWinner(i));
		}

		//全員のsessionをクローズ
		for(String user : room.user_list){
			//aController.closeSession(user);
		}


		//各変数の初期化
		game_loop = 0;
		this.room = new Room(room.room_id); // ルームのインスタンスを初期化
		this.card1 = new Card(); // card1のインスタンスを初期化
		this.card2 = new Card(); // card2のインスタンスを初期化
	}
	public boolean checkWinner(int player){
		int maxIndex = room.score_list.indexOf(room.score_list.stream().max(Integer::compareTo).get());
		if(maxIndex == player){
			return true;
		}else return false;
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
		boolean WAIT;//waitMatchの返り値を保存,true(４人集まった) or false
		room.increaseUserCount(user_id);
		//sendMessage 人の増減でクライアントに在室人数を通知する
		WAIT = room.waitMatch();
		if(WAIT){
			for(String user : room.user_list){
				sendMessage(user, "5002");
			}
		}
	}
	public void exitRoom(String user_id){
		room.decreaseUserCount(user_id);
		room.user_list.remove(user_id);
		//sendMessage 人の増減でクライアントに在室人数を通知する
	}
	public void stopUserGame(String user_id){
		room.stopUserGame(user_id);
	}


	public boolean checkRoomState(){
		if(room.user_count == 4){
			return false;
		}else{
			return true;
		}
	}


	public void checkSuccessMessage(String order) throws IOException{
		if(order.equals("1004")){
			check_success_message++;
			if(check_success_message == room.user_count){
				check_success_message = 0;
				try{
					startGame();
				}catch(IOException e){
					throw new RuntimeException(e);
				}

			}
		}else if(order.equals("1005")){
			check_success_message++;
			if(check_success_message == room.user_count){
				check_success_message = 0;
				//１枚目を表示するメッセージを送信
				displayFirstCard();


			}
		}else if(order.equals("1006")){
			check_success_message++;
			if(check_success_message == room.user_count){
				check_success_message = 0;
				//タイマーをスタート
				startTimer();

			}
		}else if(order.equals("1008")){
			check_success_message++;
			if(check_success_message == room.user_count){
				check_success_message = 0;
				//ゲームをループ
				startGame();

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

		for(String user : room.user_list){
			sendMessage(user, "5005");
		}


	}

	public void calculateScore(String user_id, String choice, String pattern){

		//カードの数字,絵柄を確定
		int card_1;
		int card_2;
		String card_pattern;//正解の絵柄
		card_1 = card1.card_number.get(game_loop - 1) % 13;
		card_2 = card2.card_number.get(game_loop - 1) % 13;

		//絵柄判定用
		if(checkCardPattern(card2.card_number.get(game_loop - 1)) == 0){
			card_pattern = "spade";
		}else if(checkCardPattern(card2.card_number.get(game_loop - 1)) == 1){
			card_pattern = "club";
		}else if(checkCardPattern(card2.card_number.get(game_loop - 1)) == 2){
			card_pattern = "diamond";
		}else if(checkCardPattern(card2.card_number.get(game_loop - 1)) == 3){
			card_pattern = "heart";
		}else{
			card_pattern = null;
		}


		//High&Low&Justを判定
		String correct_choice;//正解の選択肢
		if(card_1 == card_2){
			correct_choice = "just";
		}else if(card_1 > card_2){
			correct_choice = "low";
		}else{
			correct_choice = "high";
		}
		//choiceがnullでなければ、点数計算
		//high low +2 +0
		//just +5 +0
		//pattern +1 -1
		int score = 0;
		int current_player_score;
		int current_hits;
		if(choice != null){
			//high
			if(correct_choice.equals(choice) && correct_choice.equals("high")){
				if(card_pattern.equals(pattern)){
					score = 3;
					current_hits = room.hit_list.get(room.user_list.indexOf(user_id));
					room.hit_list.set(room.user_list.indexOf(user_id), current_hits + 1);
				}else{
					score = -1;
				}
			}
			//low
			if(correct_choice.equals(choice) && correct_choice.equals("low")){
				if(card_pattern.equals(pattern)){
					score = 3;
					current_hits = room.hit_list.get(room.user_list.indexOf(user_id));
					room.hit_list.set(room.user_list.indexOf(user_id), current_hits + 1);
				}else{
					score = -1;
				}
			}
			//just
			if(correct_choice.equals(choice) && correct_choice.equals("just")){
				if(card_pattern.equals(pattern)){
					score = 6;
					current_hits = room.hit_list.get(room.user_list.indexOf(user_id));
					room.hit_list.set(room.user_list.indexOf(user_id), current_hits + 1);
				}else{
					score = -1;
				}
			}
		}
		current_player_score = room.score_list.get(room.user_list.indexOf(user_id));
		if(current_player_score == 0 && score < 0){
		}else{
			room.score_list.set(room.user_list.indexOf(user_id), current_player_score + score);
		}


	}

	public void choiceDeckAndCardList(){
		//５２枚ぶっこみ用
		List<String> all_card_list ;
		//all_card_list = aController.choiceDeckAndCardList();
		//0-51までを配列に突っ込む
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
			//card1.saveCard(all_card_list.get(num), num + 1);
		}

		//２枚目を保存
		for(Integer num : selected_5_from_20){
			//card2.saveCard(all_card_list.get(num), num + 1);
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


	public void sendMessage(String user_id, String order){
		Message message = new Message(user_id, order);
		if(order.equals("5003")){
			message.messageContent.room_id = room.room_id;
			message.messageContent.user_list = room.user_list;
			message.messageContent.score_list = room.score_list;
		}else if(order.equals("5004")){
			message.messageContent.room_id = room.room_id;
			message.messageContent.pattern_list = pattern_list;
			message.messageContent.image_data = card1.image.get(game_loop - 1);
		}else if(order.equals("5005")){
			message.messageContent.room_id = room.room_id;
			message.messageContent.score_list = room.score_list;
			message.messageContent.image_data = card2.image.get(game_loop - 1);
		}else if(order.equals("5006")){
			message.messageContent.room_id = room.room_id;
			message.messageContent.score_list = room.score_list;
		}else if(order.equals("5002")){
			message.messageContent.room_id = room.room_id;
		}

			//aController.sendMessage(message, user_id);


	}

	public int checkRoomCount(){
		return room.user_count;
	}


}

