import meggy.Meggy;

class RecTest {
	public static void main(String[] s) {
		new Sample().recurse((byte)2);
	}
}

class Sample {
	public void recurse(byte b) {
		if (!(b < 0)) {
			Meggy.delay(10);
			this.recurse((byte)(b-1));
		}
	}
}
