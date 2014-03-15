import meggy.Meggy;

class promote {
	public static void main(String[] s) {
		Meggy.setPixel((byte)0, (byte)0, Meggy.Color.YELLOW);
		while (Meggy.getPixel((byte)0, (byte)0) == Meggy.Color.YELLOW) {
			if (Meggy.Button.A == Meggy.Button.Up) {
				Meggy.setPixel((byte)(3+(byte)-3), (byte)(7-7), Meggy.Color.BLUE);
			}
		}
	}
}
