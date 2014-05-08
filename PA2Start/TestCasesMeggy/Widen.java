import meggy.Meggy;

class Widen {
	public static void main(String[] s) {
		if (new Sample().compare((byte)8)) {
			Meggy.delay(400);
		}
	}
}

class Sample {
	public boolean compare(int i) {
		return i < 100;
	}
}
