module com.example.hl_appserver {
	requires javafx.controls;
	requires javafx.fxml;
	requires javax.websocket.api;
	requires com.google.gson;
	requires tyrus.server;


	opens com.example.hl_appserver to javafx.fxml;
	exports com.example.hl_appserver;
}