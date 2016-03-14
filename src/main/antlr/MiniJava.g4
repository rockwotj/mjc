grammar MiniJava;

program : mainClassDecl (classDecl)* ;
mainClassDecl : 'class' Id '{' 'public' 'static' 'void' 'main' '(' 'String' '[' ']' Id')' '{' (stmt)* '}' '}' ;
classDecl : 'class' Id ('extends' Id)? '{' (classVarDecl)* (methodDecl)* '}' ;
classVarDecl : type Id ';' ;
methodDecl : 'public' type Id '(' formal (',' formal)* ')' '{' (stmt)* 'return' expr ';' '}' ;
formal : type Id;
type : 'int' | 'boolean' | Id ;
stmt : type Id  '=' expr ';'
          | '{' (stmt)* '}'
          | 'if' '(' expr ')' stmt 'else' stmt
          | 'while' '(' expr ')' stmt
          | 'System.out.println' '(' expr')' ';'
          | Id  '=' expr ;
expr : expr ('+' | '-' | '*' | '/' | '<' | '<=' | '>=' | '>' | '==' | '!=' | '&&' | '||') expr
       | ( '-' | '!' ) expr
       | expr '.' Id '(' expr (',' expr)* ')'
       | 'new ' Id '()'
       | Id
       | 'this'
       | Integer
       | 'null'
       | 'true'
       | 'false'
       | '(' expr ')' ;

//fragment Program : (Token | Whitespace)* ;
//Token : ID | Integer | ReservedWord | Operator | Delimiter ;
ReservedWord : 'class' | 'public' | 'static' | 'extends' | 'void' | 'int' | 'boolean' | 'if' |
'else' | 'while' | 'return' | 'null' | 'true' | 'false' | 'this' | 'new' |
'String' | 'main' | 'System.out.println' ;
Id : Letter (Letter | Digit)* ;
Integer : NonZeroDigit Digit* | '0' ;
Letter : [a-z] | [A-Z] ;
Digit : [0-9] ;
NonZeroDigit : [1-9] ;
Operator : '+' | '-' | '*' | '/' | '<' | '<=' | '>=' | '>' | '==' | '!=' | '&&' | '||' | '!' ;
Delimiter : ';' | '.' | ',' | '=' | '(' | ')' | '{' | '}' | '[' | ']' ;
Whitespace : (' ' | '\t' | '\n' | '\r' | Comment) -> channel(HIDDEN);
Comment : ('//' ~('\r' | '\n')* | '/*' .*? '*/') -> channel(HIDDEN);
