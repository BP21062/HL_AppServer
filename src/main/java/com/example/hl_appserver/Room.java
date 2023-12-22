package com.example.hl_appserver;

import java.util.ArrayList;
import java.util.List;

public class Room{
	int room_id;
	int user_count;
	List<String> score_list = new ArrayList<>();
	List<String> user_list = new ArrayList<>();
	int timer;
	List<Integer> pattern_list = new ArrayList<>();

	public Room(int room_id){
		this.room_id = room_id;
		user_count = 0;
	}

	public void increaseUserCount(String user_id){
		user_count += 1;
		user_list.add(user_id);
		waitMatch();
	}


	public void decreaseUserCount(String user_id){
		user_count -= 1;
		user_list.remove(user_id);
	}

	/*
	public void enterRoom(int room_id){

	}
	*/
	public void waitMatch(){
		if(user_count == 4){
			//GC startGame();
		}
	}


	public void checkCurrentPoint(){
	}

	public void startTimer(){
	}

	public void saveResult(){
	}

	public void getFinalResult(){
	}

	public void stopUserGame(int user_id){
	}
}

