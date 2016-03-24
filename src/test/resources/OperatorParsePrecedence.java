
class OperatorTest {
    public static void main(String[] args) {
        boolean bool = -1 == true;
        int aa = 1 * false;
        int a = 1 * 2 + 3;
        int b = 1 + 2 * 3;
        int c = (1 + 2) * false;
        int d = - (1 + 2) * 3;
        int e = 1 + -2 * 3;
        boolean g = 1 + 3 < 4;
        boolean f = -1 > 2 * -4;
        boolean h = true && false || false;
        boolean f = true || 2 < 3 && !false;
        boolean z =  false && 5 > 1 * -3 || true;
        boolean z =  false && (5 > 1 * -3 || true);
        FacMethod mthd = new FacMethod();
    }
}