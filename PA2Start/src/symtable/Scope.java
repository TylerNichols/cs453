package symtable;
import java.util.*;
/* Scope class for SymTable's Scope Stack */
public class Scope {

	private final HashMap<String, STE> mHashMap = new HashMap<String, STE>();
	private Scope mEnclosing;
	private final ArrayList<String> syms = new ArrayList<String>();
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

	public List<String> getSyms() {
		return syms;
	}
	public STE lookupInnermost(String sym) {
		return mHashMap.get(sym);
	}

	public void insert(STE entry) {
		syms.add(entry.getName());
		mHashMap.put(entry.getName(), entry);
	}
}
