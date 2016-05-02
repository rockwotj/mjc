class Main {
    public static void main(String[] args) {
        Foo f = new Foo();
        System.out.println(f.bar(4));
    }
}

class Foo {
    int a;
    boolean b;

    public int bar(int c) {
        return c + c;
    }
}