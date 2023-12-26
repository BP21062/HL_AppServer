package com.example.hl_appserver;

import java.util.ArrayList;
import java.util.List;

public class MessageContent{

	String user_id;
	String password;
	//String score;
	int num_plays_score;
	int num_hits_score;
	int num_wins_score;
	//String rule;
	//カードおよびルールの画像ファイル(base64)格納用のリスト
	List<String> textDataList = new ArrayList<>();
	String error;

}
