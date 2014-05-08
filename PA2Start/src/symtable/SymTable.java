package symtable;
import java.util.*;
import ast.node.*;

import java.io.PrintStream;
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
    }

    public void insertAndPushScope(NamedScopeSTE ste) {
	    insert(ste);
	    pushScope(ste.getName());
    }

    public void pushScope(String id) throws InternalException {
	    STE curr = lookup(id);
	    if (curr != null) {
		    if (curr instanceof NamedScopeSTE) {
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
    }

    public void outputDot(PrintStream out) {
	    out.println("digraph SymTable {");
	    out.println("	graph [rankdir=\"LR\"];");
	    out.println("	node [shape=record];");
	    int cElem = 0;
	    int pElem = 0;
	    out.println("	" + pElem + printCurrentScope(out));
	    printClasses(out, 0);

	    out.println("}");
	    out.flush();

    }

    private void printVarSTE(PrintStream out, String name, int parent) {
	    VarSTE curr = lookupVar(name);

	    out.print("	" + parent + " [label=\"<f0> VarSTE");
	    out.print(" | <f1> mName = " + curr.getName());
	    out.print(" | <f2> mType = " + curr.getType());
	    out.println(" | <f3> mBase = INVALID | <f4> mOffset = 0\"];");
    }

    private int printVars(PrintStream out, int parent) {
	    int elem = parent;
	    int param = 0;
	    for (String curr : mScopeStack.peek().getSyms()) {
		    out.print("	" + parent + ":<f" + ++param + "> -> ");
		    out.println(++elem + ":<f0>;");
		    printVarSTE(out, curr, elem);
	    }
	    return ++elem;
    }

    private int printMethodSTE(PrintStream out, String name, int parent) {
	    MethodSTE curr = lookupMethod(name);

	    out.print("	" + parent + " [label=\"<f0> MethodSTE");
	    out.print(" | <f1> mName = " + curr.getName());
	    Object[] params = curr.getParams().toArray();
	    out.print(" | <f2> mSignature = (");
	    if (params.length > 0)
		    for (int i = 0; i < params.length; i++) {
			    out.print((Type)params[i]);
			    if (i + 1 != params.length)
				    out.print(", ");
		    }
	    out.print(") returns " + curr.getReturnType() + ";");
	    out.println(" | <f3> mScope \"];");

	    pushScope(name);
	    out.println("	" + parent + ":<f3> -> " + ++parent + ":<f0>;");
	    out.println("	" + parent + printCurrentScope(out));
	    int elem = printVars(out, parent);
	    popScope();
	    return elem;
    }

    private int printMethods(PrintStream out, int parent) {
	    int elem = parent;
	    int num = 0;
	    for (String curr : mScopeStack.peek().getSyms()) {
		    out.print("	" + parent + ":<f" + ++num + "> -> ");
		    out.println(++elem + ":<f0>;");
		    elem = printMethodSTE(out, curr, elem);
	    }
	    return elem;
    }

    private int printClassSTE(PrintStream out, String name, int parent) {
	    ClassSTE curr = lookupClass(name);

	    out.print("	" + parent + " [label=\"<f0> ClassSTE");
	    out.print(" | <f1> mName = " + curr.getName());
	    out.print(" | <f2> mMain = false | <f3> mSuperClass = null");
	    out.println(" | <f4> mScope \"];");

	    pushScope(name);
	    out.println("	" + parent + ":<f4> -> " + ++parent + ":<f0>;");
	    out.println("	" + parent + printCurrentScope(out));
	    int elem = printMethods(out, parent);
	    popScope();
	    return elem;
    }

    private void printClasses(PrintStream out, int parent) {
	    int elem = 1;
	    int num = 0;
	    for (String curr : mScopeStack.peek().getSyms()) {
		    out.print("	" + parent + ":<f" + ++num + "> -> ");
		    out.println(elem + ":<f0>;");
		    elem = printClassSTE(out, curr, elem);
	    }
    }

    private String printCurrentScope(PrintStream out) {
	    String res = "[label=\" <f0> Scope";
	    int elem = 1;
	    for (String curr : mScopeStack.peek().getSyms()) {
		    res += " | <f" + elem + "> mDict\\[" + curr + "\\]";
		    elem++;
	    }
	    res += " \"];";
	    return res;
    }
}
