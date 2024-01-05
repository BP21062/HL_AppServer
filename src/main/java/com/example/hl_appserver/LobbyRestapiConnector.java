package com.example.hl_appserver;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;


@Path("/")
public class LobbyRestapiConnector{

	public AController aController;
	static Gson gson = new Gson();

	public LobbyRestapiConnector(AController aController){
		this.aController = aController;
	}


	@Path("/checkRoomState")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * checkRoomStateメソッド
	 * ルームに入れるかどうかをJsonで返す
	 * trueの場合には、AServerConnectorにuser_idを記憶する。
	 * @param request 受け取ったJson(room_idとuser_idが埋まっているもの)
	 * @return 真理値　部屋に入れるならtrue 入れなければfalse
	 */ public Response checkRoomState(String request){
		//メッセージを解凍
		Message request_message = gson.fromJson(request, Message.class);
		//checkRoomStateがtrueならuser_idを記憶
		if(aController.checkRoomState(request_message.messageContent.room_id)){
			aController.memorizeUser(request_message.messageContent.user_id);
		}
		//よくよく考えればbooleanだけ返せばよくね...？ part.1
		return Response.ok().entity(gson.toJson(aController.checkRoomState(request_message.messageContent.room_id))).build();
	}

	@Path("/checkRoomCount")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * checkRoomCountメソッド
	 * ルームの在室人数をJsonで返す MessageContentクラスのroom_user_countに部屋１～６の順で返す
	 * @return List<Integer> 部屋部屋１～６の人数
	 */ public Response checkRoomCount(){
		//よくよく考えればListだけ返せばよくね...？ part.2
		return Response.ok().entity(gson.toJson(aController.checkRoomCount())).build();

	}
}
