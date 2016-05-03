class Main {
    public static void main(String[] args) {
        Foo f = new Foo();
        Foo ff = null;
        System.out.println(f.bar(4));
    }
}

class Foo {
    public int bar(int c) {
        return c + c;
    }

    public Foo baz() {
        return null;
    }

    public boolean isNine(int a, int b, int c) {
        return a + b + c == 9;
    }
}