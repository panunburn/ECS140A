/* *** This file is given as part of the programming assignment. *** */
import java.util.*;

public class Parser {


    // tok is global to all these parsing methods;
    // scan just calls the scanner's scan method and saves the result in tok.
    private Token tok; // the current token
    private void scan() {
	tok = scanner.scan();
    }

    private Scan scanner;
    Parser(Scan scanner) {
	this.scanner = scanner;
	scan();
    symtable = new SymTable();
      nameList = new ArrayList<Pair>();
	program();
	if( tok.kind != TK.EOF )
	    parse_error("junk after logical end of program");
    }

    private SymTable symtable;
  
  private void printPtimes(int n){
    for (int i = 0; i<n; i++){
      System.out.print("x_");
    }
  }
  
  private ArrayList<Pair> nameList;
    private void program() {
      System.out.println("#include <stdio.h>");
      System.out.println("int main(){");
	block();
      System.out.println("return 0;");
      System.out.println("}");
    }

    private void block(){
      symtable.push();
	declaration_list();
	statement_list();
      ArrayList<String> top = symtable.top();
      for (String n : top){
        for (Pair temm : nameList){
          if (temm.getFirst().equals(n)){
            temm.minusOne();
          }
        }
    }
      symtable.pop();
    }
    private void declaration_list() {
	// below checks whether tok is in first set of declaration.
	// here, that's easy since there's only one token kind in the set.
	// in other places, though, there might be more.
	// so, you might want to write a general function to handle that.
	while( is(TK.DECLARE) ) {
	    declaration();
	}
    }

    private void declaration() {
	mustbe(TK.DECLARE);
      if (is(TK.ID)) {
        checkRedeclare();
      }
	mustbe(TK.ID);
	while( is(TK.COMMA) ) {
	    scan();
        checkRedeclare();
	    mustbe(TK.ID);
	}
    }

  private void checkRedeclare() {
    boolean exist = false;
    int number = 0;
    if (symtable.curExist(tok.string)){
      System.err.println("redeclaration of variable "+tok.string);
    }
    else{
      symtable.addVar(tok.string);
      for (Pair item : nameList){
        if (item.getFirst().equals(tok.string)){
          item.addOne();
          exist = true;
          number = item.getSecond();
        }
      }
      if (exist == false){//no same name
        Pair thisP = new Pair(tok.string,1);
        nameList.add(thisP);
        number = 1;
      }
      System.out.print("int ");
      printPtimes(number);
      System.out.println(tok.string+";");
    }
  }
  
    private void statement_list() {
      while (is(TK.ID) || is(TK.ASSIGN) || is(TK.DO) || is(TK.IF) || is(TK.TILDE) || is(TK.PRINT) || is(TK.FOR)){
        statement();
      }
    }

  private void statement() {
    if (is(TK.PRINT)){
      myprint();
    }
    else if (is(TK.DO)){
      mydo();
    }
    else if (is(TK.IF)){
      myif();
    }
    else if (is(TK.TILDE) || is(TK.ID)){
      assignment();
    }
    else if (is(TK.FOR)){
      myfor();
    }
  }
  
  private void myfor() {
    mustbe(TK.FOR);
    System.out.print("for (");
    if (!is(TK.SEP))
    {
      assignment2();
    }
    mustbe(TK.SEP);
    System.out.print(";");
    if (!is(TK.SEP)){
      factor();
      condition();
      factor();
    }
    mustbe(TK.SEP);
    System.out.print(";");
    if (!is(TK.SEP)){
      assignment2();
    }
    mustbe(TK.SEP);
    System.out.println(") {");
    block();
    System.out.println("}");
    scan();
  }
  
  private void mydo() {
    mustbe(TK.DO);
    System.out.print("while ( 0>= ");
    guarded_cmd();
    mustbe(TK.ENDDO);
  }
  
  private void condition() {
    if (is(TK.ASSIGN)){
      mustbe(TK.ASSIGN);
      mustbe(TK.ASSIGN);
      System.out.print("==");
    }
    else if(is(TK.DO)){
      mustbe(TK.DO);
      System.out.print("<");
      if (is(TK.ASSIGN)){
        System.out.print("=");
        scan();
      }
    }
    else if (is(TK.ENDDO)){
      mustbe(TK.ENDDO);
      System.out.print(">");
      if (is(TK.ASSIGN)){
        System.out.print("=");
        scan();
      }
    }
    else if (is(TK.PRINT)){
      mustbe(TK.PRINT);
      mustbe(TK.ASSIGN);
      System.out.print("!=");
    }
  }
  
  private void myif() {
    scan();
    System.out.print("if ( 0>= ");
    guarded_cmd();
    while (is(TK.ELSEIF)){
      scan();
      System.out.print("else if (0 >= ");
      guarded_cmd();
    }
    if (is(TK.ELSE)) {
      scan();
      System.out.println("else {");
      block();
      System.out.println("}");
    }
    mustbe(TK.ENDIF);
  }
  
  private void guarded_cmd() {
    expr();
    System.out.print(")");
    mustbe(TK.THEN);
    System.out.println("{");
    block();
    System.out.println("}");
  }
  
  private void assignment() {
    ref_id();
    mustbe(TK.ASSIGN);
    System.out.print("=");
    expr();
    System.out.println(";");
  }
  
  private void assignment2() { //this assignment without a appending semicolon is for the for loop
    ref_id();
    mustbe(TK.ASSIGN);
    System.out.print("=");
    expr();
  }
  
  private void myprint() {
    mustbe(TK.PRINT);
    System.out.print("printf(\"%d\\n\", ");
    expr();
    System.out.println(");");
  }
  
  private void expr() {
    term();
    while (is(TK.PLUS) || is(TK.MINUS)){
      System.out.print(tok.string);
      scan();
      term();
    }
  }
  
  private void term() {
    factor();
    while (is(TK.TIMES) || is(TK.DIVIDE)){
      System.out.print(tok.string);
      scan();
      factor();
    }
  }
  
  private void factor() {
    if (is(TK.LPAREN)){
      System.out.print("(");
      scan();
      expr();
      mustbe(TK.RPAREN);
      System.out.print(")");
    }
    else if (is(TK.ID) || is(TK.TILDE)){
      ref_id();
    }
    else if (is(TK.NUM)){
      System.out.print(tok.string);
      mustbe(TK.NUM);
    }
    else{
      //System.err.println("want factor");
    }
  }
  
  private void ref_id() {
    int number = 0;
    if (is(TK.TILDE)){//WITH [~[NUM]]
      scan();
      int num = -1;//global
      if (is(TK.NUM)){//WITH [NUM]
        num = Integer.parseInt(tok.string);
        scan();
      }
      checkScopeDeclare(num);//exist in that scope
    
      if (num == -1){//global
        number = 1;
      }
      else{
        for (Pair item : nameList){
          if (item.getFirst().equals(tok.string)){//match
            int total = item.getSecond();
            for (int i = 0; i < num; i++){//scan n scope
              if (symtable.scopeExist(tok.string,i))
                total--;
            }
            number = total;
          }
        }
      }
      printPtimes(number);
      System.out.print(tok.string);
      scan();
      return;
    }
    
    if (is(TK.ID)){
      checkHasDeclare();
      for (Pair item : nameList){
        if (item.getFirst().equals(tok.string)){//match
          number = item.getSecond();
        }
      }
      printPtimes(number);
      System.out.print(tok.string);
    }
    mustbe(TK.ID);
  }
  
  private void checkScopeDeclare(int scope) {
    if (scope == -1){
      if (!symtable.scopeExist(tok.string,symtable.maxscope())){
        System.err.println("no such variable ~"+tok.string+" on line "+tok.lineNumber);
        System.exit(1);
      }
    }
    else{
      if (!symtable.scopeExist(tok.string, scope)){
        System.err.println("no such variable ~"+scope+tok.string+" on line "+tok.lineNumber);
        System.exit(1);
      }
    }
  }
  
  private void checkHasDeclare() {
    if (!symtable.exist(tok.string)){
      System.err.println(tok.string+" is an undeclared variable on line "+tok.lineNumber);
      System.exit(1);
    }
  }
        
        
        
    // is current token what we want?
    private boolean is(TK tk) {
        return tk == tok.kind;
    }

    // ensure current token is tk and skip over it.
    private void mustbe(TK tk) {
	if( tok.kind != tk ) {
	    System.err.println( "mustbe: want " + tk + ", got " +
				    tok);
	    parse_error( "missing token (mustbe)" );
	}
	scan();
    }

    private void parse_error(String msg) {
	System.err.println( "can't parse: line "
			    + tok.lineNumber + " " + msg );
	System.exit(1);
    }
}
