package com.example.hl_appserver;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameController{
	public Room room; //紐づいてるルームのインスタンス
	public Card card1 = new Card(); //１枚目のカードを５枚保存する
	public Card card2 = new Card(); //２枚目のカードを５枚保存する
	public int check_success_message = 0; //クライアントの遷移確認用

	public int game_loop = 0; //ループ回数の保存用
	public List<Integer> pattern_list = new ArrayList<>(); //２０枚の絵柄管理用

	public GameController(int room_id){
		if(1 <= room_id && room_id <= 6){
			this.room = new Room(room_id); // ルームのインスタンスを初期化
		}
		this.card1 = new Card(); // card1のインスタンスを初期化
		this.card2 = new Card(); // card2のインスタンスを初期化

	}

	public void startGame(){
		//game_loopを1増やす
		game_loop++;
		if(game_loop == 1){
			//カードを山から選択して保存
			choiceDeckAndCardList();
			//現在のスコアを表示
			displayCurrentPoint();
		}else if(game_loop == 6 || room.user_count == 1){

			endGame();
		}else{
			//現在のスコアを表示
			displayCurrentPoint();
		}

	}

	public void endGame(){
		//最終スコアの送信
		for(String user : room.user_list){
			sendMessage(user, "5006");
		}
		//戦績の保存
		for(int i = 0; i < room.user_list.size(); i++){
			AController.recordResult(room.user_list.get(i), room.hit_list.get(i), checkWinner(i));
		}

		this.game_loop = 0;
		this.card1 = new Card(); // card1のインスタンスを初期化
		this.card2 = new Card(); // card2のインスタンスを初期化

		List <String> user_list_temp = new ArrayList<>();
		//全員のsessionをクローズ
		for(String user : room.user_list){
			user_list_temp.add(user);
		}

		// 全部消したために狂っているのではないかと考察⇒正しかった
		for(String user : user_list_temp){
			AController.closeSession(user);
		}


		//各変数の初期化
		this.room = new Room(room.room_id); // ルームのインスタンスを初期化

	}

	public boolean checkWinner(int player){
		int maxIndex = room.score_list.indexOf(room.score_list.stream().max(Integer::compareTo).get());
		if(maxIndex == player){
			return true;
		}else
			return false;
	}

	public void displayCurrentPoint(){
		for(String user : room.user_list){
			sendMessage(user, "5003");
		}
	}

	public void displayFirstCard(){
		System.out.println(room.user_list);
		for(String user : room.user_list){
			sendMessage(user, "5004");
		}
	}

	public void enterRoom(String user_id){//引数をroom_idからuser_idに変更
		room.increaseUserCount(user_id);
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


	public void checkSuccessMessage(String order){
		if(order.equals("1004")){
			check_success_message++;
			System.out.println("[App] checkSuccessMessage 1004 count: " + check_success_message);
			System.out.println("[App] checkSuccessMessage 1004 inroom: " + room.user_count);
			if(check_success_message == room.user_count){
				check_success_message = 0;

				startGame();

			}
		}else if(order.equals("1005")){
			check_success_message++;
			System.out.println("[App] checkSuccessMessage 1005 count: " + check_success_message);
			System.out.println("[App] checkSuccessMessage 1005 inroom: " + room.user_count);
			if(check_success_message == room.user_count){
				check_success_message = 0;
				//１枚目を表示するメッセージを送信
				displayFirstCard();
				
			}
		}else if(order.equals("1006")){
			check_success_message++;
			System.out.println("[App] checkSuccessMessage 1006 count: " + check_success_message);
			System.out.println("[App] checkSuccessMessage 1006 inroom: " + room.user_count);
			if(check_success_message == room.user_count){
				check_success_message = 0;
				//タイマーをスタート
				startTimer();

			}
		}else if(order.equals("1008")){
			check_success_message++;
			System.out.println("[App] checkSuccessMessage 1008 count: " + check_success_message);
			System.out.println("[App] checkSuccessMessage 1008 inroom: " + room.user_count);
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

	public int setCardNumber(int num){
		if(num % 13 == 0){
			return 13;
		}else
			return (num % 13);
	}

	public void calculateScore(String user_id, String choice, String pattern){

		//カードの数字,絵柄を確定
		int card_1;
		int card_2;
		String card_pattern;//正解の絵柄
		card_1 = setCardNumber(card1.card_number.get(game_loop - 1));
		card_2 = setCardNumber(card2.card_number.get(game_loop - 1));

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
			//選んだカードがhigh
			if(choice.equals("high")){
				//正解がhigh
				if(correct_choice.equals(choice)){
					score = score + 2;
					if(card_pattern.equals(pattern)){
						//絵柄も的中
						score = score + 1;
						current_hits = room.hit_list.get(room.user_list.indexOf(user_id));
						room.hit_list.set(room.user_list.indexOf(user_id), current_hits + 1);
					}else if(pattern != null){
						//絵柄的中なし
						score = score - 1;
					}
				}
			}
			//low
			//選んだカードがlow
			if(choice.equals("low")){
				//正解がhigh
				if(correct_choice.equals(choice)){
					score = score + 2;
					if(card_pattern.equals(pattern)){
						//絵柄も的中
						score = score + 1;
						current_hits = room.hit_list.get(room.user_list.indexOf(user_id));
						room.hit_list.set(room.user_list.indexOf(user_id), current_hits + 1);
					}else if(pattern != null){
						//絵柄的中なし
						score = score - 1;
					}
				}
			}
			//just
			//選んだカードがjust
			if(choice.equals("just")){
				//正解がhigh
				if(correct_choice.equals(choice)){
					score = score + 5;
					if(card_pattern.equals(pattern)){
						//絵柄も的中
						score = score + 1;
						current_hits = room.hit_list.get(room.user_list.indexOf(user_id));
						room.hit_list.set(room.user_list.indexOf(user_id), current_hits + 1);
					}else if(pattern != null){
						//絵柄的中なし
						score = score - 1;
					}
				}
			}
		}
		current_player_score = room.score_list.get(room.user_list.indexOf(user_id));
		room.score_list.set(room.user_list.indexOf(user_id), current_player_score + score);


	}

	public void choiceDeckAndCardList(){
		//５２枚ぶっこみ用
		List<String> all_card_list;
		all_card_list = AController.choiceDeckAndCardList();
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
			card1.saveCard(all_card_list.get(num), num + 1);
		}

		//２枚目を保存
		for(Integer num : selected_5_from_20){
			card2.saveCard(all_card_list.get(num), num + 1);
		}


		for(int i = 0; i < 4; i++){
			pattern_list.add(i);
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
		if(num % 13 == 0){
			return (int) (num / 13) - 1;
		}else
			return (int) (num / 13);
	}


	public void sendMessage(String user_id, String order){
		Message message = new Message(order, user_id);
		if(order.equals("5003")){
			message.messageContent.room_id = room.room_id;
			message.messageContent.game_loop = game_loop;
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
			message.messageContent.user_list = room.user_list;
		}else if(order.equals("5002")){
			message.messageContent.room_id = room.room_id;
		}

		AController.sendMessage(message, user_id);


	}

	public int checkRoomCount(){
		return room.user_count;
	}


}

