class Main {
    public static void main(String[] args) {
        Foo[] F = new Foo[2];
        F[0] = new Foo();
        int unused = F[0].set(4);
        Foo f = new Foo();
        unused = f.set(8);
        F[1] = f;
        System.out.println(F[0].get());
        System.out.println(F[1].get());
        System.out.println(f.get());
        Foo[] F2 = new Foo[5];
        F2 = F;
        System.out.println(F2[0].get());
        System.out.println(F2[1].get());
        System.out.println(f.get());
    }
}

class Foo {
    int x;

    public int set(int v) {
        x = v;
        return x;
    }

    public int get() {
        return x;
    }
}