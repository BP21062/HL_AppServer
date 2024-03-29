package com.example.hl_appserver;

import java.util.ArrayList;
import java.util.List;


public class Room{
	public int room_id;
	public int user_count;
	public List<String> user_list = new ArrayList<>();
	public List<Integer> score_list = new ArrayList<>();
	public List<Integer> hit_list = new ArrayList<>(); //戦績管理用


	public Room(int room_id){
		this.room_id = room_id;
		this.user_count = 0;
	}

	public void increaseUserCount(String user_id){
		this.user_count += 1;
		this.score_list.add(0);
		this.hit_list.add(0);
		this.user_list.add(user_id);
	}


	public void decreaseUserCount(String user_id){
		user_count -= 1;
		score_list.remove(user_list.indexOf(user_id));
		hit_list.remove(user_list.indexOf(user_id));
		user_list.remove(user_id);
	}



	public void stopUserGame(String user_id){
		decreaseUserCount(user_id);
	}
}
