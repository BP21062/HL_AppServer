package com.example.hl_appserver;

public class GameControllerContent{

	public static GameController game1;
	public static GameController game2;
	public static GameController game3;
	public static GameController game4;
	public static GameController game5;
	public static GameController game6;
	public static Room room1;
	public static Room room2;
	public static Room room3;
	public static Room room4;
	public static Room room5;
	public static Room room6;
	public static AControllerContents aControllerContents;
	public static AServerConnector aServerConnector;

	public void generateGC(int room_id){
		GameController gameController = new GameController(room_id);
		if(room_id == 1){
			game1 = gameController;
		}else if(room_id == 2){
			game2 = gameController;
		}else if(room_id == 3){
			game3 = gameController;
		}else if(room_id == 4){
			game4 = gameController;
		}else if(room_id == 5){
			game5 = gameController;
		}else if(room_id == 6){
			game6 = gameController;
		}else{
			System.out.println("room_idが不正です");
			// エラー起こすなりなんなり
		}
	}

	public void generateRoom(int room_id){
		Room room = new Room(room_id);
		if(room_id == 1){
			room1 = room;
		}else if(room_id == 2){
			room2 = room;
		}else if(room_id == 3){
			room3 = room;
		}else if(room_id == 4){
			room4 = room;
		}else if(room_id == 5){
			room5 = room;
		}else if(room_id == 6){
			room6 = room;
		}else{
			System.out.println("room_idが不正です");
			// エラー起こすなりなんなり
		}
	}

	public void generateASC(){
		AServerConnector aServerConnector_ = new AServerConnector();
		aServerConnector = aServerConnector_;
	}

}
