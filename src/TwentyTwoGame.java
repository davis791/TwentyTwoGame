import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;

/**
 * 
 * @author ryan
 *
 */
public class TwentyTwoGame {
	
	public static int playerCount;
	public static int cardsToDeal;
	public static int round;
	public static ArrayList<Cards> cardsOut = new ArrayList<Cards>();
	public static ArrayList<Player> players = new ArrayList<Player>();
	public static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("How many players");
		int playerCount = scan.nextInt();
		for (int i = 0; i < playerCount; i++) {
			players.add(new Player(0));
		}
		
		Hand startHand = new Hand(players, 7, round, cardsOut);
		
		
		
	}

}
