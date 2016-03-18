grammar MiniJava;

program : mainClassDecl (classDecl)* ;
mainClassDecl : 'class' ID '{' 'public' 'static' 'void' 'main' '(' 'String' '[' ']' ID')' '{' (stmt)* '}' '}' ;
classDecl : 'class' ID ('extends' ID)? '{' (classVarDecl)* (methodDecl)* '}' ;
classVarDecl : type ID ';' ;
methodDecl : 'public' type ID ('(' (formal (',' formal)*)? ')' | '()') '{' (stmt)* 'return' expr ';' '}' ;
formal : type ID;
type : 'int' | 'boolean' | ID ;
stmt :      varDecl
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


expr : expr '.' ID '(' expr (',' expr)* ')'
        | logicalOr;

logicalOr : logicalAnd '||' logicalOr
            | logicalAnd;

logicalAnd : equalsOrNotEquals '&&' logicalAnd
                | equalsOrNotEquals;

equalsOrNotEquals : relation '==' equalsOrNotEquals
            | relation '!=' equalsOrNotEquals
            | relation;

relation : plusOrMinus '<=' relation
            | plusOrMinus '>=' relation
            | plusOrMinus '>' relation
            | plusOrMinus '<' relation
            | plusOrMinus;

plusOrMinus : multOrDiv PLUS plusOrMinus
        | multOrDiv MINUS plusOrMinus
        | multOrDiv ;

multOrDiv : unary '*' multOrDiv
        | unary '/' multOrDiv
        | unary ;

unary : BANG expr
         | MINUS expr
         | atom;

atom : INTEGER
       | ID
       | BOOL
       | 'new ' ID '()'
       | '(' expr ')'
       | 'null'
       | 'this';


//fragment Program : (Token | Whitespace)* ;
//Token : ID | Integer | ReservedWord | Operator | Delimiter ;

ID : LETTER (LETTER | DIGIT)* ;
BOOL : TRUE | FALSE;
TRUE : 'true';
FALSE : 'false';
INTEGER : NONZERODIGIT DIGIT* | '0' ;
BANG: '!';
MINUS: '-';
PLUS: '+';

LETTER : [a-z] | [A-Z] ;
DIGIT : [0-9] ;
NONZERODIGIT : [1-9] ;

// Eat this stuff
Whitespace : (' ' | '\t' | '\n' | '\r' | Comment) -> channel(HIDDEN);
Comment : ('//' ~('\r' | '\n')* | '/*' .*? '*/') -> channel(HIDDEN);
