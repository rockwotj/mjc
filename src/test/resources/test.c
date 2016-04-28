#include <stdio.h>
//
//typedef struct {
//   int   foo;
//} Test;
//
//int Test_init(Test* this) {
//    (*this).foo = 3;
//    return 0;
//}
//
//int Test_test(int n, Test* this) {
//    return n + (*this).foo;
//}
//
//int main() {
//    Test t;
//    Test_init(&t);
//    printf("%d\n", Test_test(2, &t));
//    return 0;
//}

int main() {
       int i = 0;
      int j = 1;
test:
    i += 1;
    if (i > 3) {
        goto exit;
    }
    goto test;
exit:
    printf("%d\n", i);
    return 0;
}