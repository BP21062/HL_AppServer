package com.example.hl_appserver;

public class AController{

	public GameController game1 = new GameController(1);
	public GameController game2 = new GameController(2);
	public GameController game3 = new GameController(3);
	public GameController game4 = new GameController(4);
	public GameController game5 = new GameController(5);
	public GameController game6 = new GameController(6);


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
	}

	public void startTimer(int room_id){

	}

	public void calculateScore(int room_id){

	}

	public void checkSuccessMessage(int room_id){
	}

	public static void main(String[] args){
	}


}
