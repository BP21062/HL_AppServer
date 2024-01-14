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

	// これはインスタンスにつき1つだけ生成されるはず
	GameControllerContent(){
		room1 = new Room(1);
		room2 = new Room(2);
		room3 = new Room(3);
		room4 = new Room(4);
		room5 = new Room(5);
		room6 = new Room(6);

		game1 = new GameController(room1);
		game2 = new GameController(room2);
		game3 = new GameController(room3);
		game4 = new GameController(room4);
		game5 = new GameController(room5);
		game6 = new GameController(room6);


	}
}
