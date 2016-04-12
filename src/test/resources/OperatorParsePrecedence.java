
class OperatorTest {
    public static void main(String[] args) {
        System.out.println(new FacMethod().factorial(1 + 2 * 3));
        int num = (1 - -2);
        boolean bool = -1 == true;
        int a = 1 * 2 + 3;
        int b = 1 + 2 * 3;
        int d = -(1 + 2) * 3;
        int e = 1 + -2 * 3;
        boolean g = 1 + 3 < 4;
        boolean f = -1 > 2 * -4;
        boolean h = true && false || false;
//         boolean f = true || 2 < 3 && !false;
        boolean z = false && 5 > 1 * -3 || true;
        boolean zz = false && (5 > 1 * -3 || true);
        FacMethod mthd = new FacMethod();
         mthd = new FacMethod2();
//         boolean z = 5 == false;
        while (bool) {
            int x = 1 + 2;
        }
//         FacMethod mthd = new FacMethod();
        while (true) {

        }
        while (
                true
                )

        {

        }
        while (true) {
        }
        int retval = num * mthd.factorial(num - 1);
        int lhs = 0;
        int rhs = 3;
        retval = lhs - lhs / rhs * rhs;
    }
}

class FacMethod {

    int a;
    int b;

    public int factorial(int a) {
        a = 2;
        b = 3;
        FacMethod f = this;
        return 1 + a;
    }
}

class FacMethod2 extends FacMethod {


    public int foo(int a) {
        FacMethod2 f = this;
        return 1;
    }

}