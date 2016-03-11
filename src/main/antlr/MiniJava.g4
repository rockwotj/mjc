grammar MiniJava;

type : 'int' | 'boolean' | ID;


fragment Program : (Token | Whitespace)* ;
Token : ID | Integer | ReservedWord | Operator | Delimiter ;
ID : Letter (Letter | Digit)* ;
Letter : [a-z] | [A-Z] ;
Digit : [0-9] ;
NonZeroDigit : [1-9] ;
Integer : NonZeroDigit Digit* | '0' ;
ReservedWord : 'class' | 'public' | 'static' | 'extends' | 'void' | 'int' | 'boolean' | 'if' |
'else' | 'while' | 'return' | 'null' | 'true' | 'false' | 'this' | 'new' |
'String' | 'main' | 'System.out.println' ;
Operator : '+' | '-' | '*' | '/' | '<' | '<=' | '>=' | '>' | '==' | '!=' | '&&' | '||' | '!ß' ;
Delimiter : ';' | '.' | ',' | '=' | '(' | ')' | '{' | '}' | '[' | ']' ;
Whitespace : ' ' | '\t' | '\n' | '\r' | Comment ;
Comment : '//' ~('\r' | '\n')* | '/*' .*? '*/' ;