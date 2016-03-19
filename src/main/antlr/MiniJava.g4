grammar MiniJava;

program : mainClassDecl (classDecl)* ;
mainClassDecl : 'class' ID '{' 'public' 'static' 'void' 'main' '(' 'String' '[' ']' ID')' '{' (stmt)* '}' '}' ;
classDecl : 'class' ID ('extends' ID)? '{' (classVarDecl)* (methodDecl)* '}' ;
classVarDecl : type ID ';' ;
methodDecl : 'public' type ID ('(' (formal (',' formal)*)? ')' | '()') '{' (stmt)* 'return' expr ';' '}' ;
formal : type ID;
type : 'int' | 'boolean' | ID ;
stmt :
      varDecl
    | block
    | ifElse
    | whileDecl
    | print
    | assigment ;
varDecl : type ID  '=' expr ';' ;
block : '{' (stmt)* '}' ;
ifElse : 'if' '(' expr ')' stmt 'else' stmt ;
whileDecl : 'while' '(' expr ')' stmt ;
print : 'System.out.println' '(' expr')' ';' ;
assigment : ID  '=' expr ';' ;

expr :
      expr DOT ID LPAREN expr (COMMA expr)* RPAREN
    | logicalOr ;

logicalOr :
      logicalAnd OR logicalOr
    | logicalAnd ;

logicalAnd :
      equalsOrNotEquals AND logicalAnd
    | equalsOrNotEquals;

equalsOrNotEquals :
      relation (EEQ|NEQ) equalsOrNotEquals
    | relation ;

relation :
      plusOrMinus (LEQ|GEQ|LT|GT) relation
    | plusOrMinus ;

plusOrMinus :
      multOrDiv (ADD|SUB) plusOrMinus
    | multOrDiv ;

multOrDiv :
      unary (MUL|DIV) multOrDiv
    | unary ;

unary :
      (BANG|NEGATIVE) expr
    | atom ;

atom :
      INT
    | ID
    | BOOL
    | NULL
    | THIS
    | NEW ID LPAREN RPAREN
    | LPAREN expr RPAREN ;

// Reserved Words
NULL : 'null' ;
THIS : 'this' ;
NEW  : 'new' ;

// Identifier
ID : LETTER (LETTER | DIGIT)* ;
LETTER : [a-z] | [A-Z] ;

// Types
INT   : NONZERODIGIT DIGIT* | '0' ;
DIGIT : [0-9] ;
NONZERODIGIT : [1-9] ;
BOOL  : TRUE | FALSE ;
FALSE : 'false' ;
TRUE  : 'true' ;

// Logic operators
AND : '&&' ;
OR  : '||' ;

// Relational operators
EEQ : '==' ;
NEQ : '!=' ;
LEQ : '<=' ;
GEQ : '>=' ;
LT  : '<=' ;
GT  : '>=' ;

// Unary operators
BANG : '!' ;
NEGATIVE : '-' ;

// Arithmetic operators
ADD : '+' ;
SUB : '-' ;
MUL : '*' ;
DIV : '/' ;

// Delimeters
LPAREN : '(' ;
RPAREN : ')' ;
DOT    : '.' ;
COMMA  : ',' ;

Whitespace : (' ' | '\t' | '\n' | '\r' | Comment) -> channel(HIDDEN);
Comment : ('//' ~('\r' | '\n')* | '/*' .*? '*/') -> channel(HIDDEN);
