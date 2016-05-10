class Main {
    public static void main(String[] args) {
        {
            {
                int i = 3;
                System.out.println(i);
            }
            boolean i = true;
            if (i) {
                int j = 2;
                System.out.println(j);
            } else {
                int j = 0;
                System.out.println(j);
            }
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
