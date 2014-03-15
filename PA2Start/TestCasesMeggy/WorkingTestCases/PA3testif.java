import meggy.Meggy;

class PA3testif {
	public static void main(String[] something) {
		if (false) {
			Meggy.setPixel((byte)1, (byte)2, Meggy.Color.YELLOW);
		} else {}
		if (true) {
			Meggy.setPixel((byte)1, (byte)2, Meggy.Color.BLUE);
		} else {}
	}
}
