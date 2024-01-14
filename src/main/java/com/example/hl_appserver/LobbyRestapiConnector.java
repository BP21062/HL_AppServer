package com.example.hl_appserver;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import com.google.gson.Gson;

// @Path("/")
// public class LobbyRestapiConnector {

@Path("/")
public class LobbyRestapiConnector{
	static Gson gson = new Gson();

// 			System.out.println("[App] checkRoomState from Lobby: " + request_message.messageContent.room_id);

// 			// checkRoomStateがtrueならuser_idを記憶
// 			Boolean result = aControllerContents.checkRoomState(request_message.messageContent.room_id);

	public Response checkRoomState(String request){
		//メッセージを解凍
		Message request_message = gson.fromJson(request, Message.class);
		//checkRoomStateがtrueならuser_idを記憶
		if(AController.checkRoomState(request_message.messageContent.room_id)){
			AController.memorizeUser(request_message.messageContent.user_id);
		}
		//よくよく考えればbooleanだけ返せばよくね...？ part.1
		return Response.ok().entity(gson.toJson(AController.checkRoomState(request_message.messageContent.room_id))).build();
	}

}
