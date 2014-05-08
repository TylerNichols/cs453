package ast_visitors;

/** 
 * CheckTypes
 * 
 * This AST visitor traverses a MiniJava Abstract Syntax Tree and checks
 * for a number of type errors.  If a type error is found a SymanticException
 * is thrown
 * 
 * CHANGES to make next year (2012)
 *  - make the error messages between *, +, and - consistent <= ??
 *
 * Bring down the symtab code so that it only does get and set Type
 *  for expressions
 */

import ast.node.*;
import ast.visitor.DepthFirstVisitor;
import java.util.*;

import symtable.SymTable;
import symtable.*;
import exceptions.InternalException;
import exceptions.SemanticException;

public class CheckTypes extends DepthFirstVisitor
{
    
   private SymTable mCurrentST;
   private ClassSTE currClass;
   
   public CheckTypes(SymTable st) {
     if(st==null) {
          throw new InternalException("unexpected null argument");
      }
      mCurrentST = st;
      currClass = null;
   }

   
   //========================= Overriding the visitor interface

   public void defaultOut(Node node) {
       System.err.println("Node not implemented in CheckTypes, " + node.getClass());
   }
   
   public void outAndExp(AndExp node)
   {
     if(this.mCurrentST.getExpType(node.getLExp()) != Type.BOOL) {
       throw new SemanticException(printPos(node) +
         "Invalid left operand type for operator &&",
         node.getLExp().getLine(), node.getLExp().getPos());
     }

     if(this.mCurrentST.getExpType(node.getRExp()) != Type.BOOL) {
       throw new SemanticException(printPos(node) +
         "Invalid right operand type for operator &&",
         node.getRExp().getLine(), node.getRExp().getPos());
     }

     this.mCurrentST.setExpType(node, Type.BOOL);
   }

   public void outBlockStatement(BlockStatement node)
   {
	   // Errors should be caught at statement level so there's no type
	   // checking to do here
	   this.mCurrentST.setExpType(node, Type.VOID);
   }

   public void outButtonExp(ButtonLiteral node)
   {
	   this.mCurrentST.setExpType(node, Type.BUTTON);
   }

   public void outByteCast(ByteCast node)
   {
	   Type expType = this.mCurrentST.getExpType(node.getExp());
	   if ((expType == Type.INT) || (expType == Type.BYTE)) {
		   this.mCurrentST.setExpType(node, Type.BYTE);
	   } else {
		   throw new SemanticException(printPos(node) +
			"Only ints can be cast to bytes: ",
			node.getExp().getLine(), node.getExp().getPos());
	   }
   }

   public void outColorExp(ColorLiteral node)
   {
	   this.mCurrentST.setExpType(node, Type.COLOR);
   }

   public void outEqualExp(EqualExp node)
   {
	   Type lexpType = this.mCurrentST.getExpType(node.getLExp());
	   Type rexpType = this.mCurrentST.getExpType(node.getRExp());
	   if ((lexpType == rexpType) || ((lexpType == Type.INT) && (rexpType == Type.BYTE))
			   || ((lexpType == Type.BYTE) && (rexpType == Type.INT))) {
		   this.mCurrentST.setExpType(node, Type.BOOL);
	   } else {
		   throw new SemanticException(
				   "Operands of == operator must be of same type",
				   node.getLExp().getLine(),
				   node.getLExp().getPos());
	   }
   }

   public void outFalseExp(FalseLiteral node)
   {
	   this.mCurrentST.setExpType(node, Type.BOOL);
   }

   public void outIfStatement(IfStatement node)
   {
	   Type expType = this.mCurrentST.getExpType(node.getExp());
	   Type thenType = this.mCurrentST.getExpType(node.getThenStatement());
	   Type elseType = this.mCurrentST.getExpType(node.getElseStatement());

	   if (expType != Type.BOOL) {
		   System.out.println(expType);
		   throw new SemanticException(
				   "If condition must be of type BOOL",
				   node.getExp().getLine(),
				   node.getExp().getPos());
	   }

	   this.mCurrentST.setExpType(node, Type.VOID);
   }

   public void outIntegerExp(IntLiteral node)
   {
	   this.mCurrentST.setExpType(node, Type.INT);
   }

   public void outMainClass(MainClass node)
   {
	   // No Type Checking to do here yet with no STRING Type
	   this.mCurrentST.setExpType(node, Type.VOID);
   }

   public void outMeggyDelay(MeggyDelay node)
   {
	   Type argType = this.mCurrentST.getExpType(node.getExp());
	   if ((argType == Type.INT) || (argType == Type.BYTE)) {
		   this.mCurrentST.setExpType(node, Type.VOID);
	   } else {
		   throw new SemanticException(printPos(node) +
				   "Argument to Meggy.delay must be of type INT or BYTE",
				   node.getExp().getLine(),
				   node.getExp().getPos());
	   }
   }

   public void outMeggyCheckButton(MeggyCheckButton node)
   {
	   Type argType = this.mCurrentST.getExpType(node.getExp());
	   if (argType == Type.BUTTON) {
		   this.mCurrentST.setExpType(node, Type.BOOL);
	   } else {
		   throw new SemanticException(printPos(node) +
				   "Argument of Meggy.checkButton must be BUTTON",
				   node.getExp().getLine(),
				   node.getExp().getPos());
	   }
   }

   public void outMeggyGetPixel(MeggyGetPixel node)
   {
	   Type xType = this.mCurrentST.getExpType(node.getXExp());
	   Type yType = this.mCurrentST.getExpType(node.getYExp());
	   if ((xType == Type.BYTE) && (yType == Type.BYTE)) {
		   this.mCurrentST.setExpType(node, Type.COLOR);
	   } else {
		   throw new SemanticException(printPos(node) +
				   "Arguments to Meggy.getPixel must be BYTE",
				   node.getXExp().getLine(),
				   node.getXExp().getPos());
	   }
   }

   public void outMeggySetPixel(MeggySetPixel node)
   {
	   Type xType = this.mCurrentST.getExpType(node.getXExp());
	   Type yType = this.mCurrentST.getExpType(node.getYExp());
	   Type colType = this.mCurrentST.getExpType(node.getColor());
	   if ((xType == Type.BYTE) && (yType == Type.BYTE) &&
			   (colType == Type.COLOR)) {
		   this.mCurrentST.setExpType(node, Type.VOID);
	   } else {
		   throw new SemanticException(printPos(node) +
				   "Arguments to Meggy.setPixel must be of types BYTE, BYTE, COLOR",
				   node.getXExp().getLine(),
				   node.getXExp().getPos());
	   }
   }

   public void outMinusExp(MinusExp node)
   {
       Type lexpType = this.mCurrentST.getExpType(node.getLExp());
       Type rexpType = this.mCurrentST.getExpType(node.getRExp());
       if ((lexpType==Type.INT  || lexpType==Type.BYTE) &&
           (rexpType==Type.INT  || rexpType==Type.BYTE)
          ){
           this.mCurrentST.setExpType(node, Type.INT);
       } else {
           throw new SemanticException(printPos(node) +
                   "Operands to - operator must be INT or BYTE",
                   node.getLExp().getLine(),
                   node.getLExp().getPos());
       }

   }

   public void outMulExp(MulExp node)
   {
       Type lexpType = this.mCurrentST.getExpType(node.getLExp());
       Type rexpType = this.mCurrentST.getExpType(node.getRExp());
       if (lexpType == Type.BYTE && rexpType == Type.BYTE) {
	       this.mCurrentST.setExpType(node, Type.INT);
       } else {
	       throw new SemanticException(printPos(node) +
			       "Operands to * operator must be BYTEs, ",
			       node.getLExp().getLine(),
			       node.getLExp().getPos());
       }

   }

   public void outNegExp(NegExp node)
   {
	   Type expType = this.mCurrentST.getExpType(node.getExp());
	   if ((expType == Type.INT) || (expType == Type.BYTE)) {
		   this.mCurrentST.setExpType(node, Type.INT);
	   } else {
		   throw new SemanticException(printPos(node) +
				   "Operand of -(unary) must be INT or BYTE",
				   node.getExp().getLine(),
				   node.getExp().getPos());
	   }
   }
   public void outNotExp(NotExp node)
   {
	   Type expType = this.mCurrentST.getExpType(node.getExp());
	   if (expType == Type.BOOL) {
		   this.mCurrentST.setExpType(node, Type.BOOL);
	   } else {
		   throw new SemanticException(printPos(node) +
				   "Operand of ! must be BOOL",
				   node.getExp().getLine(),
				   node.getExp().getPos());
	   }
   }

   public void outPlusExp(PlusExp node)
   {
       Type lexpType = this.mCurrentST.getExpType(node.getLExp());
       Type rexpType = this.mCurrentST.getExpType(node.getRExp());
       if ((lexpType==Type.INT  || lexpType==Type.BYTE) &&
           (rexpType==Type.INT  || rexpType==Type.BYTE)
          ){
           this.mCurrentST.setExpType(node, Type.INT);
       } else {
           throw new SemanticException(printPos(node) +
                   "Operands to + operator must be INT or BYTE",
                   node.getLExp().getLine(),
                   node.getLExp().getPos());
       }

   }

   public void outProgram(Program node)
   {
	   this.mCurrentST.setExpType(node, Type.VOID);
   }

   public void outTrueExp(TrueLiteral node)
   {
	   this.mCurrentST.setExpType(node, Type.BOOL);
   }

   public void outWhileStatement(WhileStatement node)
   {
	   Type expType = this.mCurrentST.getExpType(node.getExp());
	   if (expType != Type.BOOL) {
		   throw new SemanticException(printPos(node) +
				   "While condition must be of type BOOL",
				   node.getExp().getLine(),
				   node.getExp().getPos());
	   }
	   this.mCurrentST.setExpType(node, Type.VOID);
   }

    public void inTopClassDecl(TopClassDecl node)
    {
	    //Scope sc = new Scope(mCurrentST.peek());
	    //ClassSTE ste = new ClassSTE(node.getName(), sc, false);
	    //currClass = ste;
	    mCurrentST.pushScope(node.getName());
	    currClass = mCurrentST.lookupClass(node.getName());
    }

    public void outTopClassDecl(TopClassDecl node)
    {
	    mCurrentST.popScope();
    }

    @Override
    public void visitTopClassDecl(TopClassDecl node)
    {
        inTopClassDecl(node);
        {
            List<VarDecl> copy = new ArrayList<VarDecl>(node.getVarDecls());
            for(VarDecl e : copy)
            {
                e.accept(this);
            }
        }
        {
            List<MethodDecl> copy = new ArrayList<MethodDecl>(node.getMethodDecls());
            for(MethodDecl e : copy)
            {
                e.accept(this);
            }
        }
        outTopClassDecl(node);
    }

    public void inMethodDecl(MethodDecl node)
    {
	    mCurrentST.pushScope(node.getName());
    }

    public void outMethodDecl(MethodDecl node)
    {
	    MethodSTE methodSTE = (MethodSTE)mCurrentST.lookup(node.getName());
	    if (methodSTE.getReturnType() == this.mCurrentST.getExpType(node.getExp())) {
		    this.mCurrentST.setExpType(node, methodSTE.getReturnType());
	    } else if ((methodSTE.getReturnType() == Type.VOID) && (node.getExp() == null)) {
		    this.mCurrentST.setExpType(node, Type.VOID);
	    } else throw new SemanticException(printPos(node) + "Return value of method '" + node.getName() +
			    "' does not match return type");
	    mCurrentST.popScope();
    }

    @Override
    public void visitMethodDecl(MethodDecl node)
    {
        inMethodDecl(node);
        if(node.getType() != null)
        {
            node.getType().accept(this);
        }
        {
            List<Formal> copy = new ArrayList<Formal>(node.getFormals());
            for(Formal e : copy)
            {
                e.accept(this);
            }
        }
        {
            List<VarDecl> copy = new ArrayList<VarDecl>(node.getVarDecls());
            for(VarDecl e : copy)
            {
                e.accept(this);
            }
        }
        {
            List<IStatement> copy = new ArrayList<IStatement>(node.getStatements());
            for(IStatement e : copy)
            {
                e.accept(this);
            }
        }
        if(node.getExp() != null)
        {
            node.getExp().accept(this);
        }
        outMethodDecl(node);
    }

    public void inIdLiteral(IdLiteral node)
    {
        defaultIn(node);
    }

    public void outIdLiteral(IdLiteral node) throws SemanticException
    {
	VarSTE id_ste = this.mCurrentST.lookupVar(node.getLexeme());

	if (id_ste != null) {
		this.mCurrentST.setExpType(node, id_ste.getType());
	} else throw new SemanticException(printPos(node) +
		       	"'" + node.getLexeme() + "' is not defined in this scope");
    }

    private Type iTypeToType(IType i) {
	    if (i instanceof IntType) {
		   return Type.INT;
	    } else if (i instanceof BoolType) {
		   return Type.BOOL;
	    } else if (i instanceof VoidType) {
		   return Type.VOID; 
	    } else if (i instanceof ByteType) {
		   return Type.BYTE; 
	    } else if (i instanceof ColorType) {
		   return Type.COLOR; 
	    } else if (i instanceof ButtonType) {
		   return Type.BUTTON;
	    } else if (i instanceof ToneType) {
		   return Type.TONE;
	    } else return null;
    }

    @Override
    public void visitIdLiteral(IdLiteral node)
    {
        inIdLiteral(node);
        outIdLiteral(node);
    }

    public void inCallExp(CallExp node)
    {
        defaultIn(node);
    }

    public void outCallExp(CallExp node)
    {
	    List<Type> args = new ArrayList<Type>();
	    for(IExp e : node.getArgs()) {
		    args.add(mCurrentST.getExpType(e));
	    }
	    Type retType = typeCheck(node.getExp(), node.getId(), args);
	    mCurrentST.setExpType(node, retType);/*
	    List<Type> args = new ArrayList<Type>();
	    for(IExp e : node.getArgs()) {
		    args.add(mCurrentST.getExpType(e));
	    }
	    typeCheck(node.getExp(), node.getId(), args);
	    MethodSTE mste = mCurrentST.lookupMethod(node.getId());
	    System.out.println(mCurrentST.peek().getSyms());
	    mCurrentST.setExpType(node, mste.getReturnType());*/
    }

    @Override
    public void visitCallExp(CallExp node)
    {
        inCallExp(node);
        if(node.getExp() != null)
        {
            node.getExp().accept(this);
        }
        {
            List<IExp> copy = new ArrayList<IExp>(node.getArgs());
            for(IExp e : copy)
            {
                e.accept(this);
            }
        }
        outCallExp(node);
    }

    public void inCallStatement(CallStatement node)
    {
        defaultIn(node);
    }

    public void outCallStatement(CallStatement node)
    {
	    List<Type> args = new ArrayList<Type>();
	    for(IExp e : node.getArgs()) {
		    args.add(mCurrentST.getExpType(e));
	    }
	    typeCheck(node.getExp(), node.getId(), args);
	    Type retType = mCurrentST.getExpType(node.getExp());
	    mCurrentST.setExpType(node, retType);
    }

    @Override
    public void visitCallStatement(CallStatement node)
    {
        inCallStatement(node);
        if(node.getExp() != null)
        {
            node.getExp().accept(this);
        }
        {
            List<IExp> copy = new ArrayList<IExp>(node.getArgs());
            for(IExp e : copy)
            {
                e.accept(this);
            }
        }
        outCallStatement(node);
    }

    private ClassSTE getClassSTE(IExp rec, String metName) {
	    ClassSTE c_ste = null;
	    if (mCurrentST.getExpType(rec) == Type.CLASS) {
		    if (rec instanceof NewExp) {
			    c_ste = mCurrentST.lookupClass(((NewExp)rec).getId());
		    } else if (rec instanceof ThisLiteral) {
			    c_ste = currClass;
		    }
	    }
	    return c_ste;
    }

    public Type typeCheck(IExp rec, String metName, List<Type> args) {
	    ClassSTE c_ste = getClassSTE(rec, metName);

	    if (c_ste == null)
		    throw new SemanticException("'" + rec + "' is not a valid receiver");
	    STE m_ste = c_ste.getScope().lookup(metName);

	    if (m_ste == null)
		    throw new SemanticException("'" + metName +
				    "' is not a method in '" + rec + "'");
	    if (m_ste instanceof MethodSTE) {
		    List<Type> actuals = ((MethodSTE)m_ste).getParams();
		    if (actuals.size() != args.size())
			    throw new SemanticException("Incorrect number of arguments for method '"
					    + rec + "." + metName + "(" + actuals + ")");
		    for (int i = 0; i < args.size(); ++i) {
			    if (args.get(i) != actuals.get(i))
				    if ((args.get(i) != Type.BYTE) ||
						    (actuals.get(i) != Type.INT))
					    throw new SemanticException("Arguments to '" + rec + "." + metName +
						    "' must be (" + actuals + ")");
		    }
		    return ((MethodSTE)m_ste).getReturnType();
	    } else throw new SemanticException(metName + " is not a method in " + c_ste.getName());
    }


    public void inNewExp(NewExp node)
    {
        defaultIn(node);
    }

    public void outNewExp(NewExp node)
    {
	if(mCurrentST.lookup(node.getId()) != null) {
		mCurrentST.setExpType(node, Type.CLASS);
	}
    }

    @Override
    public void visitNewExp(NewExp node)
    {
        inNewExp(node);
        outNewExp(node);
    }

    public void inFormal(Formal node)
    {
	    defaultIn(node);
    }

    public void outFormal(Formal node)
    {
	mCurrentST.setExpType(node, iTypeToType(node.getType()));
    }

    @Override
    public void visitFormal(Formal node)
    {
        inFormal(node);
        if(node.getType() != null)
        {
            node.getType().accept(this);
        }
        outFormal(node);
    }

    public void inVoidType(VoidType node)
    {
        defaultIn(node);
    }

    public void outVoidType(VoidType node)
    {
	    mCurrentST.setExpType(node, Type.VOID);
    }

    @Override
    public void visitVoidType(VoidType node)
    {
        inVoidType(node);
        outVoidType(node);
    }

    public void inByteType(ByteType node)
    {
        defaultIn(node);
    }

    public void outByteType(ByteType node)
    {
	    mCurrentST.setExpType(node, Type.BYTE);
    }

    @Override
    public void visitByteType(ByteType node)
    {
        inByteType(node);
        outByteType(node);
    }

    public void inThisExp(ThisLiteral node)
    {
        defaultIn(node);
    }

    public void outThisExp(ThisLiteral node)
    {
    }

    @Override
    public void visitThisLiteral(ThisLiteral node)
    {
        inThisExp(node);
        outThisExp(node);
    }

    public void inLtExp(LtExp node)
    {
        defaultIn(node);
    }

    public void outLtExp(LtExp node)
    {
	    if ((mCurrentST.getExpType(node.getLExp()) != Type.INT) &&
			    (mCurrentST.getExpType(node.getLExp()) != Type.BYTE)) {
		    throw new SemanticException(printPos(node) + "Invalid lefthand operator for <");
			    }
	    if ((mCurrentST.getExpType(node.getRExp()) != Type.INT) &&
			    (mCurrentST.getExpType(node.getRExp()) != Type.BYTE)) {
		    throw new SemanticException(printPos(node) + "Invalid righthand operator for <");
			    }
	    mCurrentST.setExpType(node, Type.BOOL);
    }

    @Override
    public void visitLtExp(LtExp node)
    {
        inLtExp(node);
        if(node.getLExp() != null)
        {
            node.getLExp().accept(this);
        }
        if(node.getRExp() != null)
        {
            node.getRExp().accept(this);
        }
        outLtExp(node);
    }

    public void inMeggyToneStart(MeggyToneStart node)
    {
        defaultIn(node);
    }

    public void outMeggyToneStart(MeggyToneStart node)
    {
	    if (mCurrentST.getExpType(node.getToneExp()) != Type.TONE) {
		    throw new SemanticException(printPos(node) + "First argument must be of type TONE");
	    } else if ((mCurrentST.getExpType(node.getDurationExp()) != Type.INT) &&
			    (mCurrentST.getExpType(node.getDurationExp()) != Type.BYTE)) {
		    throw new SemanticException(printPos(node) + "Second argument must be of type INT or BYTE");
	    } else
		    mCurrentST.setExpType(node, Type.VOID);
    }

    public void visitMeggyToneStart(MeggyToneStart node)
    {
        inMeggyToneStart(node);
        if(node.getToneExp() != null)
        {
            node.getToneExp().accept(this);
        }

        if(node.getDurationExp() != null)
        {
            node.getDurationExp().accept(this);
        }
        outMeggyToneStart(node);
    }

    private String printPos(Node node) {
	    return "[" + node.getLine() + ", " + node.getPos() + "]: ";
    }

    public void inToneExp(ToneLiteral node)
    {
        defaultIn(node);
    }

    public void outToneExp(ToneLiteral node)
    {
	    mCurrentST.setExpType(node, Type.TONE);
    }

    @Override
    public void visitToneLiteral(ToneLiteral node)
    {
        inToneExp(node);
        outToneExp(node);
    }

    public void inToneType(ToneType node)
    {
        defaultIn(node);
    }

    public void outToneType(ToneType node)
    {
        defaultOut(node);
    }

    @Override
    public void visitToneType(ToneType node)
    {
        inToneType(node);
        outToneType(node);
    }

    public void inBoolType(BoolType node)
    {
        defaultIn(node);
    }

    public void outBoolType(BoolType node)
    {
	    mCurrentST.setExpType(node, Type.BOOL);
    }

    @Override
    public void visitBoolType(BoolType node)
    {
        inBoolType(node);
        outBoolType(node);
    }

}
