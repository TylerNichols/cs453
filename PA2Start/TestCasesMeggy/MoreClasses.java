import meggy.Meggy;

class MoreClasses {
	public static void main(String[] s) {
		new ClassOne().method1();
		new ClassTwo().method2();
		new ClassThree().method3();
	}
}

class ClassOne {
	public void method1() {
		if (89 < 4) {
			this.shouldntHappen();
		} else {
			this.shouldHappen(50);
		}
	}

	public void shouldntHappen() {
		Meggy.delay(5000);
	}

	public void shouldHappen(int i) {
		Meggy.toneStart(Meggy.Tone.F3, i);
	}
}

class ClassTwo {
	public void method2() {
		new ClassThree().method3();
		this.nextMethod();
	}

	public void nextMethod() {
		Meggy.toneStart(Meggy.Tone.B3, 50);
	}
}

class ClassThree {

	public void shouldHappen(Meggy.Tone t) {
		Meggy.toneStart(t, 50);
	}

	public void shouldntHappen(int i) {
		Meggy.toneStart(Meggy.Tone.A3,i);
	}
	public void method3() {
		if (17 < 8964) {
			this.shouldHappen(Meggy.Tone.E3);
		} else {
			this.shouldntHappen(10);
		}
	}
}
