module com.example.hl_appserver {
	requires javafx.controls;
	requires javafx.fxml;


	opens com.example.hl_appserver to javafx.fxml;
	exports com.example.hl_appserver;
}