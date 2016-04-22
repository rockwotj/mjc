#include <stdio.h>

typedef struct {
   int   foo;
} Test;

int Test_init(Test* this) {
    (*this).foo = 3;
    return 0;
}

int Test_test(int n, Test* this) {
    return n + (*this).foo;
}

int main() {
    Test t;
    Test_init(&t);
    printf("%d\n", Test_test(2, &t));
    return 0;
}