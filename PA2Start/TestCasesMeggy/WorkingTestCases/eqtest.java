import meggy.Meggy;

class eqtest {
	public static void main(String[] s) {
		if ((byte)6 == 6) {
			Meggy.delay(100);
		} else {
			Meggy.delay(10);
		}

		if (5 == (byte)5) {
			Meggy.delay(100);
		} else {
			Meggy.delay(10);
		}

		if (Meggy.Button.Up == Meggy.Color.RED) {
			Meggy.delay(100);
		}
	}
}
