class Main {
    public static void main(String[] args) {
        Bar f = new Bar();
        System.out.println(f.print(f));
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
