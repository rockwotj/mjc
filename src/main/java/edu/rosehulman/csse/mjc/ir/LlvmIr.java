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
        // TODO: Handle classes.
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

    public void store(String dstReg, String type, String srcRegOrValue) {
        String irType = getIRType(type);
        addIRLine("store %s %s, %s* %s, align %d", irType, srcRegOrValue, irType, dstReg, getIRTypeSizeInBytes(irType));
    }

    public String load(String dstReg, String type, String srcRegOrValue) {
        String irType = getIRType(type);
        addIRLine("%s = load %s, %s* %s, align %s", dstReg, irType, irType, srcRegOrValue, getIRTypeSizeInBytes(irType));
        return dstReg;
    }

    public String add(String dstReg, String type, String srcRegOrVa1, String srcRegOrVal2) {
        String irType = getIRType(type);
        addIRLine("%s = add nsw %s %s, %s", dstReg, irType, srcRegOrVa1, srcRegOrVal2);
        return dstReg;
    }

    public String mult(String dstReg, String type, String srcRegOrVa1, String srcRegOrVal2) {
        String irType = getIRType(type);
        addIRLine("%s = mul nsw %s %s, %s", dstReg, irType, srcRegOrVa1, srcRegOrVal2);
        return dstReg;
    }

    public String div(String dstReg, String type, String srcRegOrVa1, String srcRegOrVal2) {
        String irType = getIRType(type);
        addIRLine("%s = sdiv nsw %s %s, %s", dstReg, irType, srcRegOrVa1, srcRegOrVal2);
        return dstReg;
    }

    public String minus(String dstReg, String type, String srcRegOrVa1, String srcRegOrVal2) {
        String irType = getIRType(type);
        addIRLine("%s = sub nsw %s %s, %s", dstReg, irType, srcRegOrVa1, srcRegOrVal2);
        return dstReg;
    }

    public String lessThan(String dstReg, String type, String srcRegOrVa1, String srcRegOrVal2) {
        String irType = getIRType(type);
        addIRLine("%s = icmp slt %s %s, %s", dstReg, irType, srcRegOrVa1, srcRegOrVal2);
        return dstReg;
    }

    public String greaterThan(String dstReg, String type, String srcRegOrVa1, String srcRegOrVal2) {
        String irType = getIRType(type);
        addIRLine("%s = icmp sgt %s %s, %s", dstReg, irType, srcRegOrVa1, srcRegOrVal2);
        return dstReg;
    }

    public String greaterThanEqualTo(String dstReg, String type, String srcRegOrVa1, String srcRegOrVal2) {
        String irType = getIRType(type);
        addIRLine("%s = icmp sge %s %s, %s", dstReg, irType, srcRegOrVa1, srcRegOrVal2);
        return dstReg;
    }

    public String lessThanEqualTo(String dstReg, String type, String srcRegOrVa1, String srcRegOrVal2) {
        String irType = getIRType(type);
        addIRLine("%s = icmp sle %s %s, %s", dstReg, irType, srcRegOrVa1, srcRegOrVal2);
        return dstReg;
    }

    public String bang(String dstReg, String type, String srcRegOrVa1) {
        String irType = getIRType(type);
        addIRLine("%s = icmp ne %s %s, false", dstReg, irType, srcRegOrVa1);
        return dstReg;
    }

    public String neg(String dstReg, String type, String srcRegOrVa1) {
        return minus(dstReg, type, "0", srcRegOrVa1);
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
