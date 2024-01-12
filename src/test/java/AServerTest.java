import com.example.hl_appserver.AControllerContents;


import com.example.hl_appserver.Card;
import com.example.hl_appserver.GameController;
import com.example.hl_appserver.GameControllerContent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AServerTest{
	private AControllerContents aControllerContents;
	private GameController game1;
	private GameController game2;

	private Card card1;
	private Card card2;

	@BeforeEach
	public void setUp(){
		aControllerContents = new AControllerContents();
		game1 = new GameController(1);
		game2 = new GameController(2);
		card1 = new Card();
		card2 = new Card();
		aControllerContents.game1 = this.game1;
		aControllerContents.game2 = this.game2;
		aControllerContents.game1.card1 = card1;
		aControllerContents.game1.card2 = card2;
	}

	@Test
	public void testCheckRoomState(){
		aControllerContents.game1.room.user_count = 1;
		aControllerContents.game2.room.user_count = 4;

		assertTrue(aControllerContents.checkRoomState(1));
		assertFalse(aControllerContents.checkRoomState(2));
	}

	@Test
	public void testEnterRoom(){
		aControllerContents.enterRoom(1, "NICO");
		assertEquals(aControllerContents.game1.room.user_count, 1);

		//境界値は通信が絡むため無理でした。
		//aControllerContents.game2.room.user_count = 3;
		//aControllerContents.enterRoom(2,"Ore");
	}

	@Test
	public void testStopUserGame(){
		aControllerContents.enterRoom(1, "NICO");


		aControllerContents.stopUserGame("NICO");
		assertEquals(aControllerContents.game1.room.user_count, 0);
	}

	@Test
	public void testCalculateScoreHigh(){
		aControllerContents.enterRoom(1, "NICO");
		aControllerContents.game1.game_loop = 1;
		aControllerContents.game1.card1.card_number.add(1);//S1
		aControllerContents.game1.card2.card_number.add(3);//S3

		//high +3
		aControllerContents.calculateScore(1, "NICO", "high", "spade");
		assertEquals(aControllerContents.game1.room.score_list.get(0), 3);

		//high -1
		aControllerContents.calculateScore(1, "NICO", "high", "heart");
		assertEquals(aControllerContents.game1.room.score_list.get(0), 2);

		//not score<0
		aControllerContents.game1.room.score_list.set(0, 0);
		aControllerContents.calculateScore(1, "NICO", "high", "heart");
		assertEquals(aControllerContents.game1.room.score_list.get(0), 0);

	}

	@Test
	public void testCalculateScoreJust(){
		aControllerContents.enterRoom(1, "NICO");
		aControllerContents.game1.game_loop = 1;
		aControllerContents.game1.card1.card_number.add(1);//S1
		aControllerContents.game1.card2.card_number.add(14);//C1

		// just +6
		aControllerContents.calculateScore(1, "NICO", "just", "club");
		assertEquals(aControllerContents.game1.room.score_list.get(0), 6);

		//just -1
		aControllerContents.calculateScore(1, "NICO", "just", "null");
		assertEquals(aControllerContents.game1.room.score_list.get(0), 5);
	}

	@Test
	public void testCalculateScoreLow(){
		aControllerContents.enterRoom(1, "NICO");
		aControllerContents.game1.game_loop = 1;
		aControllerContents.game1.card1.card_number.add(13);//S13
		aControllerContents.game1.card2.card_number.add(1);//S1

		//low +3
		aControllerContents.calculateScore(1, "NICO", "low", "spade");
		assertEquals(aControllerContents.game1.room.score_list.get(0), 3);

		//low -1
		aControllerContents.calculateScore(1, "NICO", "low", "null");
		assertEquals(aControllerContents.game1.room.score_list.get(0), 2);
	}
}
