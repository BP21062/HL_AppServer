module com.example.hl_appserver {
	requires javafx.controls;
	requires javafx.fxml;
	requires javax.websocket.api;
	requires com.google.gson;
	requires tyrus.server;
	requires java.ws.rs;
	requires jersey.server;
	requires grizzly.http.server;
	requires jersey.container.grizzly2.http;
	requires jersey.common;


	opens com.example.hl_appserver to javafx.fxml, com.google.gson;
	exports com.example.hl_appserver;
}