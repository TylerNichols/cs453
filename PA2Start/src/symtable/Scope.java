package symtable;
import java.util.HashMap;
/* Scope class for SymTable's Scope Stack */
public class Scope {

	private final HashMap<String, STE> mHashMap = new HashMap<String, STE>();
	private Scope mEnclosing;
	public Scope(Scope encl) {
		mEnclosing = encl;
	}

	public STE lookup(String sym) {
		if (mHashMap.containsKey(sym)) {
			return mHashMap.get(sym);
		} else if (mEnclosing == null) {
			return null;
		} else {
			return mEnclosing.lookup(sym);
		}
	}

	public void printSyms() {
		for (String s : mHashMap.keySet()) {
			System.out.println(s);
		}
	}
	public STE lookupInnermost(String sym) {
		return mHashMap.get(sym);
	}

	public void insert(STE entry) {
		mHashMap.put(entry.getName(), entry);
	}
}
