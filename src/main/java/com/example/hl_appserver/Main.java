package com.example.hl_appserver;

import java.util.List;

public class Main {

	public static void main(String[] args) {
		DatabaseConnector test = new DatabaseConnector();
		test.saveScore("Tanaka",4,true);
	}
}
