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

	public static void saveScore(String user_id, int num_hit, boolean num_wins){
		int win = 0;
		try{

			// 接続先はこんな感じの文字列->jdbc:mysql://sql.yamazaki.se.shibaura-it.ac.jp:13307/データベース名
			String target = url + ":" + sqlServerPort + "/" + sqlDatabaseName;
			System.out.println("[App] saveScore: Access to " + target);

			// 接続先情報と"MySQLへログインするための"ユーザIDとパスワードから接続を行う
			Connection connection = DriverManager.getConnection(target, sqlUserId, sqlPassword);
			// MySQLに問い合わせるためのStatementオブジェクトを構築する
			Statement stmt = connection.createStatement();
			// 実際にMySQLデータベースサーバに問い合わせるときのクエリメッセージを作る
			// ここはやりたい処理によって大きく変わることに注意
			if(num_wins){
				win = 1;
			}
			String queryString = "UPDATE SCORE SET num_plays = num_plays + 1, num_wins = num_wins + '" + win + "', num_hit = num_hit + '" + num_hit + "' WHERE user_id = '" + user_id + "';";

			// Statementオブジェクトとクエリメッセージを使い，実際に問い合わせて結果を得る
			int rs = stmt.executeUpdate(queryString);

			System.out.println(rs);

		}catch(SQLException e){
			e.printStackTrace();
		}
	}

		/**
		 * getCardListメソッド
		 * 現在は５２枚分返答しているメソッドになるが、パラメータで選定しても可
		 * DBからカードのbase64コードを取得し,string配列で返す
		 *
		 * @return 52枚のカードのbase64コードが格納されているList
		 */
		public static List<String> getCardList() {
			List<String> card_list = new ArrayList<>();
			try{

				// 接続先はこんな感じの文字列->jdbc:mysql://sql.yamazaki.se.shibaura-it.ac.jp:13307/データベース名
				String target = url + ":" + sqlServerPort + "/" + sqlDatabaseName;
				System.out.println("[App] getCardList: Access to " + target);

				// 接続先情報と"MySQLへログインするための"ユーザIDとパスワードから接続を行う
				Connection connection = DriverManager.getConnection(target, sqlUserId, sqlPassword);
				// MySQLに問い合わせるためのStatementオブジェクトを構築する
				Statement stmt = connection.createStatement();
				// 実際にMySQLデータベースサーバに問い合わせるときのクエリメッセージを作る
				// ここはやりたい処理によって大きく変わることに注意
				String queryString = "SELECT base64_code FROM CARD;";

				// Statementオブジェクトとクエリメッセージを使い，実際に問い合わせて結果を得る
				ResultSet rs = stmt.executeQuery(queryString);

				while(rs.next()){
					card_list.add(rs.getString(1));
				}


			}catch(SQLException e){
				e.printStackTrace();
			}
			return card_list;
		}
	}

