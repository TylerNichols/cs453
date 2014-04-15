package symtable;
import java.util.*;
import ast.node.*;

import exceptions.InternalException;

/** 
 * SymTable
 * ....
 * The symbol table also keeps a mapping of expression nodes to
 * types because that information is needed elsewhere especially
 * when looking up method call information.
 * 
 * @author mstrout
 * WB: Simplified to only expression types
 */
public class SymTable {
    private final HashMap<Node,Type> mExpType = new HashMap<Node,Type>();
    private final Stack<Scope> mScopeStack = new Stack<Scope>();
    private final Scope mGlobalScope = new Scope(null);

    public SymTable() {
	    mScopeStack.push(mGlobalScope);
    }
    
    public void setExpType(Node exp, Type t)
    {
    	this.mExpType.put(exp, t);
    }

    public Type getExpType(Node exp)
    {
    	return this.mExpType.get(exp);
    }

    public STE lookup(String sym) {
	    return mScopeStack.peek().lookup(sym);
    }

    public ClassSTE lookupClass(String sym) {
	    STE ste = lookup(sym);
	    if ((ste != null) && (ste instanceof ClassSTE)){
		    return (ClassSTE)ste;
	    } else return null;
    }

    public MethodSTE lookupMethod(String sym) {
	    STE ste = lookup(sym);
	    if ((ste != null) && (ste instanceof MethodSTE)) {
		    return (MethodSTE)ste;
	    } else return null;
    }

    public VarSTE lookupVar(String sym) {
	    STE ste = lookup(sym);
	    System.out.println("Looking up " + sym);
	    if ((ste != null) && (ste instanceof VarSTE)) {
		    return (VarSTE)ste;
	    } else return null;
    }

    public STE lookupInnermost(String sym) {
	    return mScopeStack.peek().lookupInnermost(sym);
    }

    public void insert(STE ste) throws InternalException {
	    STE poss = mScopeStack.peek().lookupInnermost(ste.getName()); 
	    if (poss == null) {
		    mScopeStack.peek().insert(ste);
	    } else throw new InternalException("Symbol '" + ste.getName() +
		    "' is already defined in this scope");
	    System.out.println("Inserting " + ste.getName());
    }

    public void insertAndPushScope(NamedScopeSTE ste) {
	    insert(ste);
	    pushScope(ste.getName());
    }

    public void pushScope(String id) throws InternalException {
	    STE curr = lookup(id);
	    if (curr != null) {
		    if (curr instanceof NamedScopeSTE) {
			    System.out.println("Pushing scope of " + id);
			    mScopeStack.push(((NamedScopeSTE)curr).getScope());
		    } else {
			    throw new InternalException(id + " is not a method");
		    }
	    } else {
		    throw new InternalException("Symbol '" + id + "' not found");
	    }
    }

    public Scope peek() {
	    return mScopeStack.peek();
    }

    public void popScope() {
	    if (!mScopeStack.empty())
		    mScopeStack.pop();
	    System.out.println("Pop");
    }

}
