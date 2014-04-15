package symtable;
import symtable.STE;
import symtable.Scope;

public class NamedScopeSTE extends STE {

	private Scope mScope;

	public NamedScopeSTE(String name, Scope sc) {
		super(name);
		mScope = sc;
	}

	public Scope getScope() {
		return mScope;
	}
}
