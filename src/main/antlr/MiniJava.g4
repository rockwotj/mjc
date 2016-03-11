lexer grammar MiniJava;

//program : mainClassDecl (classDecl)* ;
//mainClassDecl : 'class' ID { 'public' 'static' 'void' 'main' ('String'[] ID){(stmt)*}} ;
//classDecl : classID ['extends' ID] {(classVarDecl)* (methodDecl)* } ;
//classVarDecl : type ID ;
//methodDecl : publicType ID ([formal (,formal)*] ){(stmt)* 'return' expr; } ;
//formal : type ID ';' ;
//type : int | boolean | ID ;
//stmt : type ID  '=' expr ';'
//	   | {(stmt)*}
//	   | 'if' (expr) stmt 'else' stmt
//	   | 'while' (expr) stmt
//	   | 'System.out.println'(expr) ';'
//	   | ID  '=' expr ;
//expr : expr ('+' | '-' | '*' | '/' | '<' | '<=' | '>=' | '>' | '==' | !'=' | '&&' | '||') expr
//       | ( '-' | '!' ) expr
//       | expr '.' ID ([expr {,expr}] )
//       | 'new' ID ( )
//       | ID
//       | 'this'
//       | Integer
//       | 'null'
//       | 'true'
//       | 'false'
//       | (expr) ;

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