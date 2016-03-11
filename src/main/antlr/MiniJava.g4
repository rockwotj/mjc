lexer grammar MiniJava;

//fragment Program : (Token | Whitespace)* ;
//Token : ID | Integer | ReservedWord | Operator | Delimiter ;
ReservedWord : 'class' | 'public' | 'static' | 'extends' | 'void' | 'int' | 'boolean' | 'if' |
'else' | 'while' | 'return' | 'null' | 'true' | 'false' | 'this' | 'new' |
'String' | 'main' | 'System.out.println' ;
ID : Letter (Letter | Digit)* ;
Integer : NonZeroDigit Digit* | '0' ;
Letter : [a-z] | [A-Z] ;
Digit : [0-9] ;
NonZeroDigit : [1-9] ;
Operator : '+' | '-' | '*' | '/' | '<' | '<=' | '>=' | '>' | '==' | '!=' | '&&' | '||' | '!' ;
Delimiter : ';' | '.' | ',' | '=' | '(' | ')' | '{' | '}' | '[' | ']' ;
Whitespace : (' ' | '\t' | '\n' | '\r' | Comment) -> channel(HIDDEN);
Comment : ('//' ~('\r' | '\n')* | '/*' .*? '*/') -> channel(HIDDEN);
