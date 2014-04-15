package symtable;
import symtable.NamedScopeSTE;
import symtable.Type;
import java.util.List;

public class MethodSTE extends NamedScopeSTE {

	private List<Type> mParams;
	private Type mReturn;
	private int size;

	public MethodSTE(String name, Scope sc) {
		super(name, sc);
		mParams = null;
		mReturn = null;
		size = 0;
	}

	public MethodSTE(String name, Scope sc, List<Type> params) {
		super(name, sc);
		mParams = params;
		mReturn = null;
	}

	public MethodSTE(String name, Scope sc, List<Type> params, Type ret) {
		super(name, sc);
		mParams = params;
		mReturn = ret;
	}

	public void setParams(List<Type> p) {
		mParams = p;
	}

	public void setReturnType(Type t) {
		mReturn = t;
	}

	public void setSize(int s) {
		size = s;
	}

	public List<Type> getParams() {
		return mParams;
	}

	public Type getReturnType() {
		return mReturn;
	}

	public int getSize() {
		return size;
	}
}
