package com.example.hl_appserver;

public class Message{
	public MessageContent messageContent = new MessageContent();
	String order;
	Boolean result;

	public Message(String order, String user_id){
		this.order = order;
		this.messageContent.user_id = user_id;
	}

}
