grammar MiniJava;

program : mainClassDecl (classDecl)* ;
mainClassDecl : 'class' ID '{' 'public' 'static' 'void' 'main' '(' 'String' '[' ']' ID')' '{' (stmt)* '}' '}' ;
classDecl : 'class' ID ('extends' ID)? '{' (classVarDecl)* (methodDecl)* '}' ;
classVarDecl : type ID ';' ;
methodDecl : 'public' type ID (LPAREN (formal (',' formal)*)? RPAREN | PAREN) '{' (stmt)* 'return' expr ';' '}' ;
formal : type ID;
type : INT_TYPE | BOOL_TYPE | ID ;
stmt : varDecl
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

expr : logicalOr;

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
      (BANG|SUB) expr
    | methodCall ;

methodCall :
    callableAtom (functionCall)*
    | callableAtom;

functionCall: DOT ID LPAREN expr (COMMA expr)* RPAREN ;

callableAtom :
     THIS
    | NEW ID (PAREN | LPAREN RPAREN)
    | ID
    | LPAREN expr RPAREN
    | atom;

atom :
      INT
    | BOOL
    | NULL ;



// Reserved Words
NULL : 'null' ;
THIS : 'this' ;
NEW  : 'new' ;
BOOL_TYPE : 'boolean';
INT_TYPE : 'int';

// Types
INT   : NONZERODIGIT DIGIT* | '0' ;
DIGIT : [0-9] ;
NONZERODIGIT : [1-9] ;
BOOL  : TRUE | FALSE ;
FALSE : 'false' ;
TRUE  : 'true' ;

// Identifier
ID : LETTER (LETTER | DIGIT)* ;
LETTER : [a-z] | [A-Z] ;

// Logic operators
AND : '&&' ;
OR  : '||' ;

// Relational operators
EEQ : '==' ;
NEQ : '!=' ;
LEQ : '<=' ;
GEQ : '>=' ;
LT  : '<' ;
GT  : '>' ;

// Arithmetic operators
ADD : '+' ;
SUB : '-' ;
MUL : '*' ;
DIV : '/' ;

// Unary operators
BANG : '!' ;
//NEGATIVE : SUB ;

// Delimeters
LPAREN : '(' ;
RPAREN : ')' ;
PAREN  : '()';
DOT    : '.' ;
COMMA  : ',' ;

Whitespace : (' ' | '\t' | '\n' | '\r' | Comment) -> channel(HIDDEN);
Comment : ('//' ~('\r' | '\n')* | '/*' .*? '*/') -> channel(HIDDEN);
