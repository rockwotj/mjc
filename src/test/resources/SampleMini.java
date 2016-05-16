class Main {
    public static void main(String[] args) {
        string s = new string();
        int ignored = s.init(0);
        char blah = s.push('H');
        blah = s.push('e');
        blah = s.push('l');
        blah = s.push('l');
        blah = s.push('o');
        blah = s.push(' ');
        blah = s.push('W');
        blah = s.push('o');
        blah = s.push('r');
        blah = s.push('l');
        blah = s.push('d');
        blah = s.push('!');
        ignored = s.println();
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

class string {
    char[] l;
    int size;
    int capacity;

    public int init(int initialCapacity) {
        size = 0;
        capacity = initialCapacity;
        l = new char[capacity];
        return 0;
    }

    public char push(char val) {
        if (size >= capacity) {
            int i = 0;
            capacity = capacity * 2;
            char[] temp = new char[capacity];
            while (i < size) {
                temp[i] = l[i];
                i = i + 1;
            }
            l = temp;
        } else {}
        l[size] = val;
        size = size + 1;
        return val;
    }

    public char peek() {
        return l[size - 1];
    }

    public char pop() {
        char val = this.peek();
        size = size - 1;
        return val;
    }

    public int println() {
        int i = 0;
        while (i < size) {
            System.out.print(l[i]);
            i = i + 1;
        }
        System.out.print('\n');
        return 0;
    }
}