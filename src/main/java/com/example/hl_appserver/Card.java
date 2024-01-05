package com.example.hl_appserver;

import java.util.ArrayList;
import java.util.List;


public class Card{
	public List<Integer> card_number = new ArrayList<>();
	public List<String> image = new ArrayList<>();

	public void saveCard(String card_base64,int num){
		image.add(card_base64);
		card_number.add(num);

	}

}
