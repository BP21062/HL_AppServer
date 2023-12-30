package com.example.hl_appserver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnector{
	private static final String sqlDriverName = "com.mysql.jdbc.Driver";

	// SQLサーバの指定
	private static final String url = "jdbc:mysql://sql.yamazaki.se.shibaura-it.ac.jp";
	private static final String sqlServerPort = "13308";

	// 以下は班ごとに違うことに注意
	private static final String sqlDatabaseName = "db_group_b";
	private static final String sqlUserId = "group_b";
	private static final String sqlPassword = "group_b";

	DatabaseConnector(){
		try{
			Class.forName(sqlDriverName);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}

	public List<Integer> getScore(String user_id){
		List<Integer> scoreDataList = null;
		try{

			// 接続先はこんな感じの文字列->jdbc:mysql://sql.yamazaki.se.shibaura-it.ac.jp:13307/データベース名
			String target = url + ":" + sqlServerPort + "/" + sqlDatabaseName;
			System.out.println("target: " + target);

			// 接続先情報と"MySQLへログインするための"ユーザIDとパスワードから接続を行う
			Connection connection = DriverManager.getConnection(target, sqlUserId, sqlPassword);
			// MySQLに問い合わせるためのStatementオブジェクトを構築する
			Statement stmt = connection.createStatement();
			// 実際にMySQLデータベースサーバに問い合わせるときのクエリメッセージを作る
			// ここはやりたい処理によって大きく変わることに注意
			String queryString = "SELECT * FROM SCORE WHERE user_id = '" + user_id + "';";

			// Statementオブジェクトとクエリメッセージを使い，実際に問い合わせて結果を得る
			ResultSet rs = stmt.executeQuery(queryString);

			// 得られた結果の集合から必要なデータを取り出す
			List<String> textDataList = new ArrayList<>();
			scoreDataList = new ArrayList<>();
			// 0→plays 1→hits 2→wins


			while(rs.next()){
				System.out.println(rs.getString(1));
				scoreDataList.add(Integer.parseInt(rs.getString(2)));
				scoreDataList.add(Integer.parseInt(rs.getString(3)));
				scoreDataList.add(Integer.parseInt(rs.getString(4)));
				//textDataList.add(rs.getString(1));
			}
			System.out.println("List Elements:");
			for(int element : scoreDataList){
				System.out.println(element);
			}


		}catch(SQLException e){
			e.printStackTrace();
		}finally{

		}
		return scoreDataList;
	}

	public String getRule(){
		String rule = null;
		try{

			// 接続先はこんな感じの文字列->jdbc:mysql://sql.yamazaki.se.shibaura-it.ac.jp:13307/データベース名
			String target = url + ":" + sqlServerPort + "/" + sqlDatabaseName;
			System.out.println("target: " + target);

			// 接続先情報と"MySQLへログインするための"ユーザIDとパスワードから接続を行う
			Connection connection = DriverManager.getConnection(target, sqlUserId, sqlPassword);
			// MySQLに問い合わせるためのStatementオブジェクトを構築する
			Statement stmt = connection.createStatement();
			// 実際にMySQLデータベースサーバに問い合わせるときのクエリメッセージを作る
			// ここはやりたい処理によって大きく変わることに注意
			String queryString = "SELECT rule FROM RULE;";

			// Statementオブジェクトとクエリメッセージを使い，実際に問い合わせて結果を得る
			ResultSet rs = stmt.executeQuery(queryString);

			// 得られた結果の集合から必要なデータを取り出す


			while(rs.next()){
				System.out.println(rs.getString(1));
				rule = rs.getString(1);
			}


		}catch(SQLException e){
			e.printStackTrace();
		}
		return rule;

	}

	public void getUserList(){

	}

	public void saveScore(){
	}

	public void getCardList(){
		try{

			// 接続先はこんな感じの文字列->jdbc:mysql://sql.yamazaki.se.shibaura-it.ac.jp:13307/データベース名
			String target = url + ":" + sqlServerPort + "/" + sqlDatabaseName;
			System.out.println("target: " + target);

			// 接続先情報と"MySQLへログインするための"ユーザIDとパスワードから接続を行う
			Connection connection = DriverManager.getConnection(target, sqlUserId, sqlPassword);
			// MySQLに問い合わせるためのStatementオブジェクトを構築する
			Statement stmt = connection.createStatement();
			// 実際にMySQLデータベースサーバに問い合わせるときのクエリメッセージを作る
			// ここはやりたい処理によって大きく変わることに注意
			String queryString = "SELECT base64_code FROM CARD;";

			// Statementオブジェクトとクエリメッセージを使い，実際に問い合わせて結果を得る
			ResultSet rs = stmt.executeQuery(queryString);


			List<String> cardList = new ArrayList<>();
			while(rs.next()){
				cardList.add(rs.getString(1));
			}


		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
