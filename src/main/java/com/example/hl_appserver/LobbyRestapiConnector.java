package com.example.hl_appserver;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import com.google.gson.Gson;

@Path("/")
public class LobbyRestapiConnector {

	public AControllerContents aControllerContents;
	static Gson gson = new Gson();

	/**
	 * checkRoomStateメソッド
	 * <p>
	 * ルームに入れるかどうかをJsonで返す
	 * trueの場合には、AServerConnectorにuser_idを記憶する。
	 * 
	 * @param request 受け取ったJson(room_idとuser_idが埋まっているもの)
	 * @return 真理値 部屋に入れるならtrue 入れなければfalse
	 */
	@Path("/checkRoomState")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkRoomState(String request) {
		try {
			// メッセージを解凍
			Message request_message = gson.fromJson(request, Message.class);

			System.out.println("[App] checkRoomState from Lobby: " + request_message.messageContent.room_id);

			// checkRoomStateがtrueならuser_idを記憶
			if (aControllerContents.checkRoomState(request_message.messageContent.room_id)) {
				aControllerContents.memorizeUser(request_message.messageContent.user_id);
			}
			// よくよく考えればbooleanだけ返せばよくね...？ part.1
			return Response.ok()
					.entity(gson.toJson(aControllerContents.checkRoomState(request_message.messageContent.room_id)))
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			int status = 400;
			return Response.status(status).build();
		}

	}

	@Path("/checkRoomCount")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * checkRoomCountメソッド
	 * ルームの在室人数をJsonで返す MessageContentクラスのroom_user_countに部屋１～６の順で返す
	 * 
	 * @return List<Integer> 部屋部屋１～６の人数
	 */
	public Response checkRoomCount() {
		// よくよく考えればListだけ返せばよくね...？ part.2
		return Response.ok().entity(gson.toJson(aControllerContents.checkRoomCount())).build();

	}

}
