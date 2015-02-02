import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Hand {

	public ArrayList<Player> players;
	public int cards;
	public static ArrayList<Cards> cardsOut = new ArrayList<Cards>();
	public ArrayList<Cards> cardsLeft = new ArrayList<Cards>();
	public Random rand = new Random();
	static final Value[] VALUES = Value.values();
	static final Suit[] SUITS = Suit.values();
	public static int playerCount;
	public static int cardsToDeal;
	public static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		int playerCount;
		int cardsToDeal = 7;
		Scanner scan = new Scanner(System.in);
		System.out.println("How many players");
		playerCount = scan.nextInt();
		ArrayList<Player> playable = new ArrayList<Player>();
		for (int i = 0; i < playerCount; i++) {
			playable.add(new Player(0));
		}

		while (playable.size() > 1) {

			Hand newHand = new Hand(playable, cardsToDeal, cardsOut);

			System.out.println("What player lost?");
			int a = 0;
			while (scan.hasNextInt())
				a = scan.nextInt();
			cardsOut.add(newHand.players.get(a).hand.get(0));
			for (int i = 0; i < 13; i++) {
				if (VALUES[i].equals(newHand.players.get(a).hand.get(0).value)) {
					if (VALUES[i].equals(Value.KING) || VALUES[i].equals(Value.QUEEN) || VALUES[i].equals(Value.JACK)) {
						cardsToDeal = 10;
					}
					else if (VALUES[i].equals(Value.ACE)) {
						cardsToDeal = 11;
					}
					else {
						cardsToDeal = 14 - i;
					}
					break;
				}
			}
			newHand.players.get(a).points += cardsToDeal;
			System.out.println("What Players are out?");
			System.out.println("Player Points :");
			for (int i = 0; i < playable.size(); i++) {
				System.out.println("Player " + i + " : " + newHand.players.get(i).points);
			}
			while (scan.hasNextInt()) {
				playable.remove(scan.nextInt());
			}
		}

		scan.close();

	}
	public Hand(ArrayList<Player> players, int cards, ArrayList<Cards> cardsOut) {
		this.players = players;
		this.cards = cards;
		Hand.cardsOut = cardsOut;

		initCards(cardsOut);

		for (int j = 0; players.get(players.size() - 1).hand.size() < cards; j = (j + 1) % players.size()) {
			if (cardsLeft.size() == 0) break;
			j = j % players.size();
			int random = rand.nextInt(cardsLeft.size());
			players.get(j).hand.add(cardsLeft.get(random));
			cardsLeft.remove(random);
		}
		for (int i = 0; i < players.size(); i++) {
			sort(players.get(i));
		}
		int a = 0;
		while (!oneLeft()) {
			System.out.println("What player is starting?");
			int play = scan.nextInt();
			int peace = play;
			for (int i = 0; i < players.size(); i++) {
				sort(players.get(i));
			}
			if (a == 0) {
				for (int i = 0; i < players.size(); i++) {
					trade(play);
					play = (play + 1) % players.size();
				}
				for (int i = 0; i < players.size(); i++) {
					sort(players.get(i));
				}
			}
			play = peace;
			for (int j = 0; j < players.size(); j++) {
				@SuppressWarnings("resource")
				Scanner scan = new Scanner(System.in);
				System.out.println("Player " + play + ", What cards would you like to play?");
				for (int count = 0; count < players.get(play).hand.size(); count++) {
					System.out.println(count + ": " + players.get(play).hand.get(count).value + " of "
							+ players.get(play).hand.get(count).suit);
				}
				ArrayList<Integer> cardsToPlay = new ArrayList<Integer>();

				while (scan.hasNextInt()) {
					int i = scan.nextInt();
					cardsToPlay.add(i);
				}

				System.out.println("Cards Played: ");
				for (int i = 0; i < cardsToPlay.size(); i++) {
					Cards holder = players.get(play).hand.get(cardsToPlay.get(i));
					System.out.println(holder.value + " : " + holder.suit);
				}
				Collections.sort(cardsToPlay);
				Collections.reverse(cardsToPlay);
				for (int i = 0; i < cardsToPlay.size(); i++) {
					players.get(play).hand.remove((int)cardsToPlay.get(i));
				}
				play = (play + 1) % players.size();
			}
			a++;
		}
		System.out.println("Round result : ");
		for (int i = 0; i < players.size(); i++) {
			System.out.println("Player " + i + " : " + players.get(i).hand.get(0).value + " of "
					+ players.get(i).hand.get(0).suit);
		}
	}
	public void sort(Player player) {
		int size = player.hand.size();
		Cards[] oreo = new Cards[size];
		for (int i = 0; i < size; i++) {
			oreo[i] = player.hand.get(i);
		}
		for (int i = 0; i < size; i++) {
			player.hand.remove(0);
		}
		int[] orders = new int[size];
		int[] newOrders = new int[size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < 13; j++) {
				if (VALUES[j].equals(oreo[i].value)) {
					orders[i] = 14 - j;
				}
			}
		}
		for (int i = 0; i < size; i++) {
			int hold = 0;
			for (int j = 0; j < size; j++) {
				hold = orders[j] < orders[hold] ? j : hold;
			}
			newOrders[hold] = i;
			orders[hold] = 100;
		}
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (newOrders[j] == i) {
					player.hand.add(oreo[j]);
					break;
				}
			}
		}		
	}
	public void trade(int play) {
		Player player = players.get(play);
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.println("Player " + play + " What cards would you like to trade in? Cards Left : " + cardsLeft.size());
		for (int count = 0; count < player.hand.size(); count++) {
			System.out.println(count + ": " + player.hand.get(count).value + " of "
					+ player.hand.get(count).suit);
		}
		ArrayList<Integer> cardsToTrade = new ArrayList<Integer>();
		while (scan.hasNextInt()) {
			cardsToTrade.add(scan.nextInt());
		}
		int size = cardsToTrade.size();
		Collections.sort(cardsToTrade);
		Collections.reverse(cardsToTrade);
		for (int i = 0; i < size; i++) {
			player.hand.remove((int)cardsToTrade.get(i));
		}
		for (int j = 0; j < size; j++) {
			if (cardsLeft.size() == 0) break;
			int random = rand.nextInt(cardsLeft.size());
			player.hand.add(cardsLeft.get(random));
			cardsLeft.remove(random);
		}
	}
	public boolean oneLeft() {
		int hold = 0;
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).hand.size() == 1) {
				hold++;
			}
		}
		if (hold == players.size()) 
			return true;
		else
			return false;
	}
	public void initCards(ArrayList<Cards> cardsOut) {
		boolean safe = true;
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 4; j++) {
				for (int k = 0; k < cardsOut.size(); k++) {
					safe = (!cardsEqual(cardsOut.get(k), new Cards(VALUES[i], SUITS[j]))) ? true : false;
					if (!safe) break;
				}
				if (!safe) continue;
				cardsLeft.add(new Cards(VALUES[i], SUITS[j]));
			}
		}
	}
	public static boolean cardsEqual(Cards test, Cards card1) {
		if (cardValueEqual(test, card1) && cardSuitEqual(test, card1)) {
			return true;
		}
		else {
			return false;
		}
	}
	public static boolean cardValueEqual(Cards test, Cards card1) {
		if (test == null || card1 == null) {
			return false;
		}
		Value testVal = test.value;
		Value val1 = card1.value;
		if (testVal.equals(val1)) {
			return true;
		}
		else {
			return false;
		}
	}
	public static boolean cardSuitEqual(Cards test, Cards card1) {
		if (test == null && card1 != null) {
			return false;
		}
		Suit testSuit = test.suit;
		Suit suit1 = card1.suit;
		if (testSuit.equals(suit1)) {
			return true;
		}
		else {
			return false;
		}
	}
}
