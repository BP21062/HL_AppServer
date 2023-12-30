package com.example.hl_appserver;

public class Message{
	public MessageContent messageContent = new MessageContent();
	String order;//通信規則用
	Boolean result;//画面遷移とかのチェック用？

	public Message(String order, String user_id){
		this.order = order; this.messageContent.user_id = user_id;
	}

}
