class Main {
    public static void main(String[] args) {
        {
            {
                int i = 3;
                System.out.println(i);
            }
            int i = 2;
            System.out.println(i);
        }
        int i = 1;
        System.out.println(i);
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
