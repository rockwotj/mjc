class Main {
    public static void main(String[] args) {
        int ignored = 0;
        Printer p = new Printer();
        char[] s = new char[12];
        s[0] = 'H';
        s[1] = 'e';
        s[2] = 'l';
        s[3] = 'l';
        s[4] = 'o';
        s[5] = ' ';
        s[6] = 'W';
        s[7] = 'o';
        s[8] = 'r';
        s[9] = 'l';
        s[10] = 'd';
        s[11] = '!';
        ignored = p.printString(s, 12);

        int i = 0;
        Fib f = new Fib();
        ignored = f.init();
        /* Computer Fibonacci Sequence computing each value directly. */
        while( i < 10 ) {
            System.out.println(f.fib(i));
            i = i + 1;
        }
        i = 0;
        /* Use Fibonacci Sequence iterator. */
        while( i < 10 ) {
            System.out.println(f.next());
            i = i + 1;
        }
    }
}

class Fib {
    int current;
    int previous;

    public int init() {
        current = 0;
        previous = 0;
        return 0;
    }

    public int next(){
        int val = 0;
        if (current == 0) {
            val = 1;
        } else {
            val = current + previous;
        }
        previous = current;
        current = val;
        return current;
    }

    public int fib(int n) {
        int val = 0;
        if (n < 2) {
            val = 1;
        } else {
            val = this.fib(n - 1) + this.fib(n - 2);
        }
        return val;
    }
}

class Printer {
    public int printString(char[] s, int length) {
        int i = 0;
        while (i < length) {
            System.out.print(s[i]);
            i = i + 1;
        }
        System.out.print('\n');
        return 0;
    }
}
