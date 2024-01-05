package com.example.hl_appserver;

import java.util.ArrayList;
import java.util.List;

public class MessageContent{

	String user_id;//user_id格納用
	String password;//password格納用
	int room_id;//入りたい部屋もしくは入ってる部屋
	String choice;//選んだ選択肢を代入
	String pattern;//選んだ絵柄を代入
	List<String> user_list = new ArrayList<>();
	List<Integer> score_list = new ArrayList<>();
	List<Integer> pattern_list = new ArrayList<>();
	int num_plays_score;//戦績 play回数
	int num_hits_score;//戦績　hit回数
	int num_wins_score;//戦績　勝利数
	String image_data;//カードおよびルールの画像ファイル(base64)格納用のリスト
	String error;

}
