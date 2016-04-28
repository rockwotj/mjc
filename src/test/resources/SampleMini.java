class Main {
    public static void main(String[] args) {
        int i = 0;
        while (i < 10 || false) {
            System.out.println(i);
            i = i + 1;
        }
        System.out.println(5);
    }
}