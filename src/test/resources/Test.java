public class Main {
    public static void main(String[] args) {
        Test t = new Test();
        t.init();
        System.out.printf(t.test(2));
    }
}

class Test {
    int foo;

    public int init() {
        foo = 3;
        return 0;
    }

    public int test(int n) {
        return foo + n;
    }
}