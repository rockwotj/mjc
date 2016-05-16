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

        List list = new List();
        int ignored = list.init(2);
        ignored = list.push(42);
        System.out.println(list.peek());
        ignored = list.push(13);
        System.out.println(list.peek());
        ignored = list.push(17);
        System.out.println(list.peek());
        ignored = list.push(22);
        System.out.println(list.peek());
        System.out.println(list.pop());
        System.out.println(list.pop());
        System.out.println(list.pop());
        ignored = list.push(12);
        System.out.println(list.pop());
        System.out.println(list.pop());
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

class List {
    int[] l;
    int size;
    int capacity;

    public int init(int initialCapacity) {
        size = 0;
        capacity = initialCapacity;
        l = new int[capacity];
        return 0;
    }

    public int push(int val) {
        if (size >= capacity) {
            int i = 0;
            capacity = capacity * 2;
            int[] temp = new int[capacity];
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

    public int peek() {
        return l[size - 1];
    }

    public int pop() {
        int val = l[size - 1];
        size = size - 1;
        return val;
    }
}