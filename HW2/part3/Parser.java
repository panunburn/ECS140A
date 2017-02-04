/* *** This file is given as part of the programming assignment. *** */

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
	program();
	if( tok.kind != TK.EOF )
	    parse_error("junk after logical end of program");
    }

    private SymTable symtable;
  
    private void program() {
	block();
    }

    private void block(){
      symtable.push();
	declaration_list();
	statement_list();
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
    if (symtable.curExist(tok.string)){
      System.err.println("redeclaration of variable "+tok.string);
    }
    else{
      symtable.addVar(tok.string);
    }
  }
  
    private void statement_list() {
      while (is(TK.ID) || is(TK.ASSIGN) || is(TK.DO) || is(TK.IF) || is(TK.TILDE) || is(TK.PRINT)){
        statement();
      }
    }

  private void statement() {
    if (is(TK.PRINT)){
      myprint();
    }
    if (is(TK.DO)){
      mydo();
    }
    if (is(TK.IF)){
      myif();
    }
    if (is(TK.TILDE) || is(TK.ID)){
      assignment();
    }
  }
  
  private void mydo() {
    mustbe(TK.DO);
    guarded_cmd();
    mustbe(TK.ENDDO);
  }
  
  private void myif() {
    scan();
    guarded_cmd();
    while (is(TK.ELSEIF)){
      scan();
      guarded_cmd();
    }
    if (is(TK.ELSE)) {
      scan();
      block();
    }
    mustbe(TK.ENDIF);
  }
  
  private void guarded_cmd() {
    expr();
    mustbe(TK.THEN);
    block();
  }
  
  private void assignment() {
    ref_id();
    mustbe(TK.ASSIGN);
    expr();
  }
  private void myprint() {
    mustbe(TK.PRINT);
    expr();
  }
  
  private void expr() {
    term();
    while (is(TK.PLUS) || is(TK.MINUS)){
      scan();
      term();
    }
  }
  
  private void term() {
    factor();
    while (is(TK.TIMES) || is(TK.DIVIDE)){
      scan();
      factor();
    }
  }
  
  private void factor() {
    if (is(TK.LPAREN)){
      scan();
      expr();
      mustbe(TK.RPAREN);
    }
    else if (is(TK.ID) || is(TK.TILDE)){
      ref_id();
    }
    else if (is(TK.NUM)){
      mustbe(TK.NUM);
    }
    else{
      //System.err.println("want factor");
    }
  }
  
  private void ref_id() {
    if (is(TK.TILDE)){//WITH [~[NUM]]
      scan();
      int num = -1;
      if (is(TK.NUM)){//WITH [NUM]
        num = Integer.parseInt(tok.string);
        scan();
      }
      checkScopeDeclare(num);
    }
    if (is(TK.ID)){
      checkHasDeclare();
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
