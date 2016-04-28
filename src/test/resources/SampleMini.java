class Main {
    public static void main(String[] args) {
        int i = 0;
        boolean x = false;
        boolean y = true;
        boolean z = x && y;
        while (i < 10 || z) {
            System.out.println(i);
            i = i + 1;
        }
        System.out.println(i);
    }
}