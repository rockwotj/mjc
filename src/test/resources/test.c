#include <stdlib.h>
#include <stdio.h>

typedef struct Test Test;
int Test_test(int n, Test* this);

struct Test_methods {
    int (*Test_test)(int,Test*);
} Test_methods = {&Test_test};

struct Test {
   int   foo;
   struct Test_methods* methods;
};

int Test_test(int n, Test* this) {
    return n + this->foo;
}

int main() {
    Test* t = malloc(sizeof(Test));
    t->foo = 3;
    t->methods = &Test_methods;
    printf("%d\n", (*(t->methods->Test_test))(2, t));
    return 0;
}
