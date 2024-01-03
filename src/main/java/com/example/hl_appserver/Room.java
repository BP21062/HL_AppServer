package com.example.hl_appserver;

import java.util.ArrayList;
import java.util.List;


public class Room{
	int room_id;
	int user_count;
	List<String> user_list = new ArrayList<>();


	public Room(int room_id){
		this.room_id = room_id;
		user_count = 0;
	}

	public String increaseUserCount(String user_id){
		user_count += 1;
		user_list.add(user_id);
		if(user_count<4){
			//waitMatch();
			return "checkConnection";
		}else{
			return "startGame";
		}
	}


	public void decreaseUserCount(String user_id){
		user_count -= 1;
		user_list.remove(user_id);
	}


	public boolean waitMatch(){
		if(user_count==4){
			return true;
		}else{
			return false;
		}


		//sendMessage(クライアントの画面遷移を呼び出す)
	}


	public void stopUserGame(String user_id){
		decreaseUserCount(user_id);
	}
}
