class Main {
    public static void main(String[] args) {
        Foo f = new Foo();
        int[] a = new int[4];
        System.out.println(f.getMeaningOfLife());
        int i = 0;
        while (i < 4) {
            System.out.println(a[i]);
            i = i + 1;
        }
    }
}

class Foo {

    int life;

    public int getMeaningOfLife() {
        life = 42;
        return life;
    }

}
