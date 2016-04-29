class Main {
    public static void main(String[] args) {
        int i = 1;
        boolean x = false;
        boolean y = true;
        boolean z = x && y;
        boolean a = (false != y);
        boolean b = (a == x);
        boolean c = (11 != i);
        if (x) {
            System.out.println(1);
        } else {
            while (i <= 20 || z) {
                /* Print even numbers */
                if ((i / 2) * 2 == i ) {
                    System.out.println(i);
                } else {

                }
                i = i + 1;
            }
        }
        System.out.println(42);
    }
}