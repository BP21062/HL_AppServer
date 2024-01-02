package com.example.hl_appserver;

public class AController{
	public GameController game1 = new GameController(1, this);
	public GameController game2 = new GameController(2, this);
	public GameController game3 = new GameController(3, this);
	public GameController game4 = new GameController(4, this);
	public GameController game5 = new GameController(5, this);
	public GameController game6 = new GameController(6, this);
	public AServerConnector aServerConnector = new AServerConnector(this);

	public void displayCurrentPoint(int room_id){
	}

	public void choiceDeckAndCardList(int room_id){
	}

	public void displayChoice(int room_id){
	}

	public void displaySecondCard(int room_id){
	}

	public void changeResultScreen(int room_id){
	}

	public void recordResult(int room_id){
	}

	public boolean checkRoomState(int room_id){
		return select(room_id).checkRoomState();

		/*
		if(room_id == 1){
			return game1.checkRoomState();
		}
		else if(room_id == 2){
			return game2.checkRoomState();
		}
		else if(room_id == 3){
			return game3.checkRoomState();
		}
		else if(room_id == 4){
			return game4.checkRoomState();
		}
		else if(room_id == 5){
			return game5.checkRoomState();
		}
		else {
			return game6.checkRoomState();
		}

		 */
	}

	public void enterRoom(int room_id, String user_id){//引数にuser_idを追加
		select(room_id).enterRoom(user_id);

	}

	public boolean checkConnection(){
		aServerConnector.checkConnection();
		return true;
	}

	public void startTimer(int room_id){
		select(room_id).startTimer();

	}

	public void calculateScore(int room_id){
		select(room_id).calculateScore();
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


	public void checkSuccessMessage(int room_id){
	}

	public static void main(String[] args){
	}


}
