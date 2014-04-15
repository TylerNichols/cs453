package symtable;
import symtable.NamedScopeSTE;

public class ClassSTE extends NamedScopeSTE {

	private boolean mMain;
	private int size;

	public ClassSTE(String name, Scope sc, boolean isMain) {
		super(name, sc);
		mMain = isMain;
		size = 0;
	}

	public int getSize() {
		return size;
	}

	public boolean isMain() {
		return mMain;
	}
}
