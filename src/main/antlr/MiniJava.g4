grammar MiniJava;		
prog:	(expr Newline)* ;
expr:	expr (Multiply|Division) expr
    |	expr (Plus|Minus) expr
    |	Integer
    |	'(' expr ')'
    ;
Minus    : '-' ;
Plus     : '+' ;
Multiply : '*' ;
Division : '/' ;
Newline  : [\r\n]+ ;
Integer      : [0-9]+ ;
