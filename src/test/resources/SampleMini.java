class Main {
    public static void main(String[] args) {
        int i = 1;
        boolean x = false;
        boolean y = true;
        boolean z = x && y;

        if (x) {
            System.out.println(1);
        } else {
            while (i < 10 || z) {
                System.out.println(i);
                i = i + 1;
            }
            System.out.println(0);
        }
    }
}