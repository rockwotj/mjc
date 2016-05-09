class Main {
    public static void main(String[] args) {
        Foo f = new Bar();
        Foo a = new Foo();
        int b = 5;
        if (f != a) {
            System.out.println(f.print(f));
        } else {
            System.out.println(100);
        }
    }
}

class Foo {

    public int print(Foo f) {
        System.out.println(3);
        return 0;
    }
}

class Bar extends Foo {
    public int printAnother(int n) {
        return n;
    }
}
