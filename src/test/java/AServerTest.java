


import com.example.hl_appserver.AController;
import com.example.hl_appserver.Card;
import com.example.hl_appserver.GameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AServerTest{
	@BeforeEach
	public void setUp(){
		//testのたびに部屋を初期化
		AController.game1 = new GameController(1);
		AController.game2 = new GameController(2);
	}

	@Test
	public void testCheckRoomState(){
		//user_countのテスト
		AController.game1.room.user_count = 1;
		//境界値
		AController.game2.room.user_count = 4;

		assertTrue(AController.checkRoomState(1));
		assertFalse(AController.checkRoomState(2));
	}

	@Test
	public void testEnterRoom(){
		//Roomに正しく入室できるか
		AController.enterRoom(1, "NICO");
		assertEquals(AController.game1.room.user_count, 1);
		assertEquals(AController.game1.room.user_list.get(0),"NICO");

		//境界値は通信が絡むため無理でした。
		//aControllerContents.game2.room.user_count = 3;
		//aControllerContents.enterRoom(2,"Ore");
	}

	@Test
	public void testStopUserGame(){
		//入室させる
		AController.enterRoom(1, "NICO");

		//退出させる
		AController.stopUserGame("NICO");
		assertEquals(AController.game1.room.user_count, 0);
	}

	@Test
	public void testCalculateScoreHigh(){
		AController.enterRoom(1, "NICO");
		AController.game1.card1 = new Card();
		AController.game1.card2 = new Card();

		AController.game1.game_loop = 1;
		AController.game1.card1.card_number.add(1);//S1
		AController.game1.card2.card_number.add(3);//S3

		//high +3
		AController.calculateScore(1, "NICO", "high", "spade");
		assertEquals(AController.game1.room.score_list.get(0), 3);

		//high +2
		AController.game1.room.score_list.set(0, 0);
		AController.calculateScore(1, "NICO", "high", "heart");
		assertEquals(AController.game1.room.score_list.get(0), 1);

	}

	@Test
	public void testCalculateScoreJust(){
		AController.enterRoom(1, "NICO");
		AController.game1.card1 = new Card();
		AController.game1.card2 = new Card();

		AController.game1.game_loop = 1;
		AController.game1.card1.card_number.add(1);//S1
		AController.game1.card2.card_number.add(14);//C1

		// just +6
		AController.calculateScore(1, "NICO", "just", "club");
		assertEquals(AController.game1.room.score_list.get(0), 6);

		//just +5
		AController.game1.room.score_list.set(0, 0);
		AController.calculateScore(1, "NICO", "just", "null");
		assertEquals(AController.game1.room.score_list.get(0), 4);
	}

	@Test
	public void testCalculateScoreLow(){
		AController.enterRoom(1, "NICO");
		AController.game1.card1 = new Card();
		AController.game1.card2 = new Card();

		AController.game1.game_loop = 1;
		AController.game1.card1.card_number.add(13);//S13
		AController.game1.card2.card_number.add(1);//S1

		//low +3
		AController.calculateScore(1, "NICO", "low", "spade");
		assertEquals(AController.game1.room.score_list.get(0), 3);

		//low +2
		AController.game1.room.score_list.set(0, 0);
		AController.calculateScore(1, "NICO", "low", "null");
		assertEquals(AController.game1.room.score_list.get(0), 1);
	}
}
