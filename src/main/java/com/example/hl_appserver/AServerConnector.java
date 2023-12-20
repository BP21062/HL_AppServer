package com.example.hl_appserver;

public class AServerConnector{
	int send_db;
	int receive_db;

	public Message sendMessage(Message message){
		return message;
	}

	public void connect(String server_name){}

	public void enterRoom(int room_id){}

	public void checkRoomState(){}

	public void checkConnection(){}

	public void logout(String user_id){}

	public void getRule(){}

	public void getScore(){}

	public void getCardList(){}

	public void getErrorInfo(){}
}
