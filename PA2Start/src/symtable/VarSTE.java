package symtable;
import symtable.STE;
import symtable.Type;

/* SymTable entry for variables */
public class VarSTE extends STE {

	private Type mType;
	private char mBase;
	private int mOffset;

	public VarSTE(String name, Type t) {
		super(name);
		mType = t;
		mBase = '\0';
		mOffset = 0;
	}

	public void setType(Type t) {
		mType = t;
	}

	public void setLocation(char base, int offset) {
		mBase = base;
		mOffset = offset;
	}

	public Type getType() {
		return mType;
	}

	public char getBase() {
		return mBase;
	}

	public int getOffset() {
		return mOffset;
	}
}
