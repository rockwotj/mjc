grammar MiniJava;

program : mainClassDecl (classDecl)* ;
mainClassDecl : 'class' ID '{' 'public' 'static' 'void' 'main' '(' 'String' '[' ']' ID')' '{' (stmt)* '}' '}' ;
classDecl : 'class' ID ('extends' ID)? '{' (classVarDecl)* (methodDecl)* '}' ;
classVarDecl : type ID ';' ;
methodDecl : 'public' type ID (LPAREN (formal (',' formal)*)? RPAREN | PAREN) '{' (stmt)* 'return' expr ';' '}' ;
formal : type ID;
type : single_type | single_type LBRACKET RBRACKET ;
single_type : INT_TYPE | BOOL_TYPE | CHAR_TYPE | ID ;
stmt : varDecl
 | block
 | ifElse
 | whileDecl
 | puts
 | print
 | assigment ;
varDecl : type ID  '=' expr ';' ;
block : '{' (stmt)* '}' ;
ifElse : 'if' '(' expr ')' stmt 'else' stmt ;
whileDecl : 'while' '(' expr ')' stmt ;
puts : 'System.out.print' '(' expr ')' ';' ;
print : 'System.out.println' '(' expr ')' ';' ;
assigment :  ID LBRACKET expr RBRACKET '=' expr ';' | ID  '=' expr ';' ;

expr : logicalOr ;

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
      plusOrMinus (ADD|SUB) multOrDiv
    | multOrDiv ;

multOrDiv :
      multOrDiv (MUL|DIV) unary
    | unary ;

unary :
     (BANG|SUB) unary
    | methodCall ;

methodCall :
      methodCall DOT ID (LPAREN expr (COMMA expr)* RPAREN | PAREN)
    | atom ;

atom :
      INT
    | BOOL
    | CHAR
    | NULL
    | THIS
    | NEW ID (PAREN | LPAREN RPAREN)
    | NEW single_type LBRACKET expr RBRACKET
    | ID LBRACKET expr RBRACKET
    | ID
    | LPAREN expr RPAREN ;


// Reserved Words
NULL : 'null' ;
THIS : 'this' ;
NEW  : 'new' ;
BOOL_TYPE : 'boolean';
INT_TYPE : 'int';
CHAR_TYPE : 'char';

// Types
INT   : NONZERODIGIT DIGIT* | '0' ;
DIGIT : [0-9] ;
NONZERODIGIT : [1-9] ;
CHAR : '\'' (LETTER | DIGIT | '\\n' | '\\t' | ' ' | '.' | '!' | '?' | ';' | ':' | '$') '\'';
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
LBRACKET : '[' ;
RBRACKET : ']' ;
PAREN  : '()';
DOT    : '.' ;
COMMA  : ',' ;

Whitespace : (' ' | '\t' | '\n' | '\r' | Comment) -> channel(HIDDEN);
Comment : ('//' ~('\r' | '\n')* | '/*' .*? '*/') -> channel(HIDDEN);
