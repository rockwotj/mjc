package edu.rosehulman.csse.mjc.ir;

public class LlvmIr {

    private StringBuilder outputIR = new StringBuilder();

    public LlvmIr() {
        addIRLine("@.str = private unnamed_addr constant [4 x i8] c\"%%d\\0A\\00\", align 1");
        addIRLine("declare i32 @printf(i8*, ...)");
    }

    public void startMethod(String name, String returnType) {
        addIRLine("define %s @%s() {", getIRType(returnType), name);
    }

    private String getIRType(String returnType) {
        if (returnType.equals("int")) {
            return "i32";
        } else if (returnType.equals("boolean")) {
            return "i1";
        }
        throw new RuntimeException("Invalid Type!");
    }

    public void print(String valueOrReg) {
        addIRLine("call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.str, i32 0, i32 0), i32 %s)", valueOrReg);
    }

    public void returnStatment(String valueOrReg, String type) {
        addIRLine("ret %s %s", getIRType(type), valueOrReg);
    }

    public void endMethod() {
        addIRLine("}");
    }

    private void addIRLine(String ir, Object... args) {
        outputIR.append(String.format(ir, args)).append("\n");
    }

    @Override
    public String toString() {
        return outputIR.toString();
    }
}
