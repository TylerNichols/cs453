import meggy.Meggy;

class PA3simple {
	public static void main(String[] argv) {
		Meggy.setPixel((byte)3, (byte)5, Meggy.Color.BLUE);
		Meggy.delay(60);
	}
}
