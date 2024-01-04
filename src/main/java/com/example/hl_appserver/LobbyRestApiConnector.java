package com.example.hl_appserver;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;


@Path("/")
public class LobbyRestApiConnector{

	public AController aController;
	static Gson gson = new Gson();

	public LobbyRestApiConnector(AController aController){
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
	 * @return 真理値の詰まったJson　部屋に入れるならtrue 入れなければfalse
	 */
	public Response checkRoomState(String request){
		//メッセージを解凍
		Message request_message = gson.fromJson(request, Message.class);
		//checkRoomStateがtrueならuser_idを記憶
		if(aController.checkRoomState(request_message.messageContent.room_id)){
			aController.memorizeUser(request_message.messageContent.user_id);
		}
		//返信用のメッセージを作成　通信規則はいらないけど、コンストラクタで指定してしまっているため...(一応シーケンス図に記載の番号を詰めました)
		Message send_message = new Message("6000", request_message.messageContent.user_id);
		//部屋の入室可否をメッセージに保存
		send_message.result = aController.checkRoomState(request_message.messageContent.room_id);
		//レスポンスを返す(true falseに関わらずhttpレスポンスが200で固定になってしまうかも？？？)
		return Response.ok().entity(gson.toJson(send_message)).build();
	}

	@Path("/checkRoomCount")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * checkRoomCountメソッド
	 * ルームの在室人数をJsonで返す MessageContentクラスのroom_user_countに部屋１～６の順で返す
	 * @param request 受け取ったJson(room_idとuser_idが埋まっているもの)
	 * @return Json(room_user_countが埋まっているもの)
	 */
	public Response checkRoomCount(String request){
		//メッセージの解凍
		Message request_message = gson.fromJson(request, Message.class);
		//送信メッセージの作成()
		Message send_message = new Message("6001", request_message.messageContent.user_id);
		//部屋の人数のカウントを格納
		send_message.messageContent.room_user_count = aController.checkRoomCount();
		//メッセージを送信
		return Response.ok().entity(gson.toJson(send_message)).build();
	}
}
