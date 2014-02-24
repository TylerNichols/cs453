/*
  mj.lex
  
    Tokens/Symbols for MeggyJava language.
 
    NO dollars, but underscores anywhere in identifiers 
    (to avoid problems later when generating AVR labels from id-s)

    8 colors (not all the CPP colors)
    
    The values for the colors, buttons, and tones were found in
    MeggyJrSimple.h and MeggyJrSimple.cpp.
    

    Ignore single line comments: // until eol 
    ALSO: ignore C style comments, see http://ostermiller.org/findcomment.html

    Wim Bohm and Michelle Strout, 6/2011
  
*/

package mjparser;
import java_cup.runtime.Symbol;

%%
%cup
%line
%char
%public

%eofval{
  return new Symbol(sym.EOF, new SymbolValue(yyline, yychar+1, "EOF"));
%eofval}


EOL=(\r|\n|\r\n)

%%

/*  Special Characters  */
"+"         {return new Symbol(sym.PLUS, new SymbolValue(yyline+1, yychar+1, yytext()));}
"-"         {return new Symbol(sym.MINUS, new SymbolValue(yyline+1, yychar+1, yytext()));}
"*"			{return new Symbol(sym.TIMES, new SymbolValue(yyline+1, yychar+1, yytext()));}
"("			{return new Symbol(sym.LPAREN, new SymbolValue(yyline+1, yychar+1, yytext()));}
")"			{return new Symbol(sym.RPAREN, new SymbolValue(yyline+1, yychar+1, yytext()));}
"="			{return new Symbol(sym.ASSIGN, new SymbolValue(yyline+1, yychar+1, yytext()));}
"{"			{return new Symbol(sym.LBRACE, new SymbolValue(yyline+1, yychar+1, yytext()));}
"}"			{return new Symbol(sym.RBRACE, new SymbolValue(yyline+1, yychar+1, yytext()));}
";"			{return new Symbol(sym.SEMI, new SymbolValue(yyline+1, yychar+1, yytext()));}
"=="		{return new Symbol(sym.EQUAL, new SymbolValue(yyline+1, yychar+1, yytext()));}
"&&"		{return new Symbol(sym.AND, new SymbolValue(yyline+1, yychar+1, yytext()));}
"<"			{return new Symbol(sym.LT, new SymbolValue(yyline+1, yychar+1, yytext()));}
"!"			{return new Symbol(sym.NOT, new SymbolValue(yyline+1, yychar+1, yytext()));}
","			{return new Symbol(sym.COMMA, new SymbolValue(yyline+1, yychar+1, yytext()));}
"["			{return new Symbol(sym.LBRACKET, new SymbolValue(yyline+1, yychar+1, yytext()));}
"]"			{return new Symbol(sym.RBRACKET, new SymbolValue(yyline+1, yychar+1, yytext()));}
"."			{return new Symbol(sym.DOT, new SymbolValue(yyline+1, yychar+1, yytext()));}

/*  Reserved words   */
"main"		{return new Symbol(sym.MAIN, new SymbolValue(yyline+1, yychar+1, yytext()));}
"boolean"	{return new Symbol(sym.BOOLEAN, new SymbolValue(yyline+1, yychar+1, yytext()));}
"int"		{return new Symbol(sym.INT, new SymbolValue(yyline+1, yychar+1, yytext()));}
"byte"		{return new Symbol(sym.BYTE, new SymbolValue(yyline+1, yychar+1, yytext()));}
"false"		{return new Symbol(sym.FALSE, new SymbolValue(yyline+1, yychar+1, yytext()));}
"if"		{return new Symbol(sym.IF, new SymbolValue(yyline+1, yychar+1, yytext()));}
"else"		{return new Symbol(sym.ELSE, new SymbolValue(yyline+1, yychar+1, yytext()));}
"true"		{return new Symbol(sym.TRUE, new SymbolValue(yyline+1, yychar+1, yytext()));}
"while"		{return new Symbol(sym.WHILE, new SymbolValue(yyline+1, yychar+1, yytext()));}
"public"	{return new Symbol(sym.PUBLIC, new SymbolValue(yyline+1, yychar+1, yytext()));}
"return"	{return new Symbol(sym.RETURN, new SymbolValue(yyline+1, yychar+1, yytext()));}
"String"	{return new Symbol(sym.STRING, new SymbolValue(yyline+1, yychar+1, yytext()));}
"static"	{return new Symbol(sym.STATIC, new SymbolValue(yyline+1, yychar+1, yytext()));}
"void"		{return new Symbol(sym.VOID, new SymbolValue(yyline+1, yychar+1, yytext()));}
"this"		{return new Symbol(sym.THIS, new SymbolValue(yyline+1, yychar+1, yytext()));}
"class"		{return new Symbol(sym.CLASS, new SymbolValue(yyline+1, yychar+1, yytext()));}
"extends"	{return new Symbol(sym.EXTENDS, new SymbolValue(yyline+1, yychar+1, yytext()));}
"new"		{return new Symbol(sym.NEW, new SymbolValue(yyline+1, yychar+1, yytext()));}
"length"	{return new Symbol(sym.LENGTH, new SymbolValue(yyline+1, yychar+1, yytext()));}
"import"	{return new Symbol(sym.IMPORT, new SymbolValue(yyline+1, yychar+1, yytext()));}

/*  Reserved Phrases  */
/*  TODO:  is this correct way to set values for all symbols?  */
/*  TODO:  most vales are -1, but some symbols have a value associated with them */
/*  TODO:  see http://www.cs.colostate.edu/~cs453/yr2014/MeggyJavaInfo/meggy-java-terms.html */
"meggy.Meggy"		{return new Symbol(sym.MEGGY, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.setPixel"	{return new Symbol(sym.MEGGYSETPIXEL, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.setAuxLEDs"	{return new Symbol(sym.MEGGYSETAUXLEDS, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.toneStart"	{return new Symbol(sym.MEGGYTONESTART, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.delay"		{return new Symbol(sym.MEGGYDELAY, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.getPxiel"	{return new Symbol(sym.MEGGYGETPIXEL, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.checkButton"	{return new Symbol(sym.MEGGYCHECKBUTTON, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.Color.DARK"	{return new Symbol(sym.COLOR_LITERAL, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.Color.RED"	{return new Symbol(sym.COLOR_LITERAL, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.Color.ORANGE" {return new Symbol(sym.COLOR_LITERAL, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.Color.YELLOW"	{return new Symbol(sym.COLOR_LITERAL, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.Color.GREEN"	{return new Symbol(sym.COLOR_LITERAL, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.Color.BLUE"	{return new Symbol(sym.COLOR_LITERAL, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.Color.VIOLET"	{return new Symbol(sym.COLOR_LITERAL, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.Color.WHITE"	{return new Symbol(sym.COLOR_LITERAL, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.Button.B"	{return new Symbol(sym.BUTTON_LITERAL, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.Button.A"	{return new Symbol(sym.BUTTON_LITERAL, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.Button.Up"	{return new Symbol(sym.BUTTON_LITERAL, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.Button.Down"	{return new Symbol(sym.BUTTON_LITERAL, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.Button.Left"	{return new Symbol(sym.BUTTON_LITERAL, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.Button.Right"	{return new Symbol(sym.BUTTON_LITERAL, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.Tone.C3"	{return new Symbol(sym.TONE_LITERAL, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.Tone.Cs3"	{return new Symbol(sym.TONE_LITERAL, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.Tone.D3"	{return new Symbol(sym.TONE_LITERAL, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.Tone.Ds3"	{return new Symbol(sym.TONE_LITERAL, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.Tone.E3"	{return new Symbol(sym.TONE_LITERAL, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.Tone.F3"	{return new Symbol(sym.TONE_LITERAL, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.Tone.Fs3"	{return new Symbol(sym.TONE_LITERAL, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.Tone.G3"	{return new Symbol(sym.TONE_LITERAL, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.Tone.Gs3"	{return new Symbol(sym.TONE_LITERAL, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.Tone.A3"	{return new Symbol(sym.TONE_LITERAL, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.Tone.As3"	{return new Symbol(sym.TONE_LITERAL, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.Tone.B3"	{return new Symbol(sym.TONE_LITERAL, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.Color"	{return new Symbol(sym.MEGGYCOLOR, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.Button"	{return new Symbol(sym.MEGGYBUTTON, new SymbolValue(yyline+1, yychar+1, yytext()));}
"Meggy.Tone"	{return new Symbol(sym.MEGGYTONE, new SymbolValue(yyline+1, yychar+1, yytext()));}

/* TODO:  INT_LITERAL, ID, comments */



{EOL} {/*reset pos to -1, if 0, otherwise line 1 starts at 0, rest start at 1 */ yychar=-1;}
[ \t\r\n\f] { /* ignore white space. */ }
. { System.err.println("Illegal character: "+yytext()); }


