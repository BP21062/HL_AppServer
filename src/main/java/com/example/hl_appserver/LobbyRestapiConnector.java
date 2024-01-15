package com.example.hl_appserver;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import com.google.gson.Gson;

@Path("/")
public class LobbyRestapiConnector {
	static Gson gson = new Gson();

	@Path("/checkRoomState")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkRoomState(String request) {

		// メッセージを解凍
		Message request_message = gson.fromJson(request, Message.class);

		// ログ
		System.out.println("[App] checkRoomState from Lobby: " + request_message.messageContent.room_id);

		// checkRoomStateがtrueならuser_idを記憶
		Boolean result = AController.checkRoomState(request_message.messageContent.room_id);

		// memo_user_listに追加する
		if (result) {
			AController.memorizeUser(request_message.messageContent.user_id);
		}
		// よくよく考えればbooleanだけ返せばよくね...？ part.1
		return Response.ok().entity(gson.toJson(result)).build();
	}

}
