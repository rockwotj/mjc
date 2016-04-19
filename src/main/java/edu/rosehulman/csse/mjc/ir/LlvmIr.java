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

    private int getIRTypeSizeInBytes(String returnType) {
        if (returnType.equals("i32")) {
            return 4;
        } else if (returnType.equals("i1")) {
            return 1;
        }
        throw new RuntimeException("Invalid Type!");
    }

    public void print(String valueOrReg) {
        addIRLine("call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.str, i32 0, i32 0), i32 %s)", valueOrReg);
    }

    public void allocateStack(String reg, String type) {
        String irType = getIRType(type);
        addIRLine("%s = alloca %s, align %d", reg, irType, getIRTypeSizeInBytes(irType));
    }

    public void store(String dstReg, String type, String srcReg) {
        String irType = getIRType(type);
        addIRLine("store %s %s, i32* %s, align %d", irType, srcReg, dstReg, getIRTypeSizeInBytes(irType));
    }

    public void load(String dstReg, String type, String srcReg) {
        String irType = getIRType(type);
        addIRLine("%s = load %s, %s* %s, align %s", dstReg, irType, irType, srcReg, getIRTypeSizeInBytes(irType));
    }

    public void add(String dstReg, String type, String srcRegOrVa1, String srcRegOrVal2) {
        String irType = getIRType(type);
        addIRLine("%s = add nsw %s %s, %s", dstReg, irType, srcRegOrVa1, srcRegOrVal2);
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
