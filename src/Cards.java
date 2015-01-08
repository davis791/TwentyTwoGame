
public class Cards {
	Value value;
	Suit suit;
	public Cards (Value val, Suit sui) {
		value = val;
		suit = sui;
	}
	public int toInt() {
		Value val = this.value;
		Value[] vals = Value.values();
		for (int i = 0; i < vals.length; i++) {
			if (vals[i].equals(val)) {
				return (14 - i);
			}
			else {
				continue;
			}
		}
		return 0;
	}
}
enum Suit {
	DIAMOND, CLUB, HEART, SPADE
}
enum Value {
	ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO
}