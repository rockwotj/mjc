class Main {
    public static void main(String[] args) {
        Bar f = new Bar();
        int ignored = f.init();
        System.out.println(f.printAnother(3));
        System.out.println(-(2 + 3 * 6));
        System.out.println(-f.printAnother(2));
    }
}

class Foo {

    int a;

    public int init() {
        a = 42;
        return 0;
    }

    public int print() {
        System.out.println(a);
        return 0;
    }

}

class Bar extends Foo {
    public int printAnother(int n) {
        System.out.println(a + 3);
        return n;
    }
}
