package com.example.hl_appserver;

public class Room{
	int room_id;
	int room_name;
	int user_list=0;
	int score;
	int timer;
	int pattern_list;

	public void increaseUserCount(){
		user_list+=1;
	}

	public void decreaseUserCount(){
		user_list-=1;
	}

	public void enterRoom(int room_id){
		increaseUserCount();

	}

	public void waitMatch(){}

	public void checkCurrentPoint(){}

	public void startTimer(){}

	public void saveResult(){}

	public void getFinalResult(){}

	public void stopUserGame(int user_id){}
}
