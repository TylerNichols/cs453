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
import symtable.Type;
import exceptions.InternalException;
import exceptions.SemanticException;

public class CheckTypes extends DepthFirstVisitor
{
    
   private SymTable mCurrentST;
   
   public CheckTypes(SymTable st) {
     if(st==null) {
          throw new InternalException("unexpected null argument");
      }
      mCurrentST = st;
   }
   
   //========================= Overriding the visitor interface

   public void defaultOut(Node node) {
       System.err.println("Node not implemented in CheckTypes, " + node.getClass());
   }
   
   public void outAndExp(AndExp node)
   {
     if(this.mCurrentST.getExpType(node.getLExp()) != Type.BOOL) {
       throw new SemanticException(
         "Invalid left operand type for operator &&",
         node.getLExp().getLine(), node.getLExp().getPos());
     }

     if(this.mCurrentST.getExpType(node.getRExp()) != Type.BOOL) {
       throw new SemanticException(
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
		   throw new SemanticException(
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
		   throw new SemanticException(
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
		   throw new SemanticException(
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
		   throw new SemanticException(
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
		   throw new SemanticException(
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
           throw new SemanticException(
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
	       throw new SemanticException(
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
		   throw new SemanticException(
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
		   throw new SemanticException(
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
           throw new SemanticException(
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
		   throw new SemanticException(
				   "While condition must be of type BOOL",
				   node.getExp().getLine(),
				   node.getExp().getPos());
	   }
	   this.mCurrentST.setExpType(node, Type.VOID);
   }
}
