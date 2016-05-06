package edu.rosehulman.csse.mjc.ir;

import edu.rosehulman.csse.mjc.reflect.Class;
import edu.rosehulman.csse.mjc.reflect.Method;
import org.antlr.v4.runtime.misc.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LlvmIr {


    private StringBuilder outputIR = new StringBuilder();

    public LlvmIr(List<Class> classList) {
        addIRLine("@.str = private unnamed_addr constant [4 x i8] c\"%%d\\0A\\00\", align 1");
        addIRLine("declare i32 @printf(i8*, ...)");
        addIRLine("declare noalias i8* @calloc(i64, i64)");
        for (Class clazz : classList) {
            List<String> fieldTypes = new ArrayList<>();
            fieldTypes.addAll(clazz.getFields().values());
            clazz(clazz.getName(), fieldTypes);
            virtualTable(clazz, clazz.getMethods());
        }
        for (Class clazz : classList) {
            createVirtualTable(clazz, clazz.getMethods());
        }
    }

    private void createVirtualTable(Class clazz, List<Method> methods) {
        String className = clazz.getName();
        addIR("@%sVTable = global %%vtable.%s { ", className, className);
        int i = 0;
        for (Method m : methods) {
            String methodParentName = clazz.getMethodParent(m).getName();
            addIR("%s (%s", getIRType(m.getReturnType()), getIRType(methodParentName));
            if (m.getParams().size() > 0) {
               addIR(", ");
            }
            int j = 0;
            for (String paramType : m.getParams().values()) {
                addIR("%s", getIRType(paramType));
                j++;
                if (j != m.getParams().size()) {
                    addIR(", ");
                }
            }
            addIR(") * @%s_%s", methodParentName, m.getName());
            i++;
            if (i != methods.size()) {
                addIR(", ");
            }
        }
        addIRLine("}");
    }

    private void virtualTable(Class clazz, List<Method> methods) {
        String className = clazz.getName();
        addIR("%%vtable.%s = type { ", className);
        int i = 0;
        for (Method m : methods) {
            String methodParentName = clazz.getMethodParent(m).getName();
            addIR("%s (%s", getIRType(m.getReturnType()), getIRType(methodParentName));
            if (m.getParams().size() > 0) {
                addIR(", ");
            }
            int j = 0;
            for (String paramType : m.getParams().values()) {
                addIR("%s", getIRType(paramType));
                j++;
                if (j != m.getParams().size()) {
                    addIR(", ");
                }
            }
            addIR(") *");
            i++;
            if (i != methods.size()) {
                addIR(", ");
            }
        }
        addIRLine("}");
    }

    public void startMethod(String name, Map<String, String> params, String returnType, String nextLabel) {
        addIR("define %s @%s(", getIRType(returnType), name);
        int index = 0;
        for (Map.Entry<String, String> param : params.entrySet()) {
            addIR("%s %%%s", getIRType(param.getValue()), param.getKey());
            index++;
            if (index != params.size()) {
                addIR(", ");
            }
        }
        addIRLine(") {");
        label(nextLabel);
    }

    private String getIRType(String returnType) {
        if (returnType.equals("int")) {
            return "i32";
        } else if (returnType.equals("boolean")) {
            return "i1";
        } else {
            return "%class." + returnType + "*";
        }
    }

    private int getIRTypeSizeInBytes(String returnType) {
        if (returnType.equals("i32")) {
            return 4;
        } else if (returnType.equals("i1")) {
            return 1;
        } else {
            return 8;
        }
    }

    public void print(String dstReg, String valueOrReg) {
        addIRLine("%s = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.str, i32 0, i32 0), i32 %s)", dstReg, valueOrReg);
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
        addIRLine("%s = sdiv %s %s, %s", dstReg, irType, srcRegOrVa1, srcRegOrVal2);
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
        addIRLine("%s = xor %s %s, true", dstReg, irType, srcRegOrVa1);
        return dstReg;
    }

    public String neg(String dstReg, String type, String srcRegOrVa1) {
        return minus(dstReg, type, "0", srcRegOrVa1);
    }

    public String equals(String dstReg, String type, String srcRegOrVa1, String srcRegOrVal2) {
        String irType = getIRType(type);
        addIRLine("%s = icmp eq %s %s, %s", dstReg, irType, srcRegOrVa1, srcRegOrVal2);
        return dstReg;
    }

    public String notEquals(String dstReg, String type, String srcRegOrVa1, String srcRegOrVal2) {
        String irType = getIRType(type);
        addIRLine("%s = icmp ne %s %s, %s", dstReg, irType, srcRegOrVa1, srcRegOrVal2);
        return dstReg;
    }

    public String zeroExtend(String dstReg, String fromType, String srcRegOrVal, String toType) {
        String fromIrType = getIRType(fromType);
        String toIrType = getIRType(toType);
        addIRLine("%s = zext %s %s to %s", dstReg, fromIrType, srcRegOrVal, toIrType);
        return dstReg;
    }

    public void label(String labelName) {
        addIRLine("%s:", labelName);
    }

    public void branch(String srcReg, String label1, String label2) {
        addIRLine("br i1 %s, label %%%s, label %%%s", srcReg, label1, label2);
    }

    public void jump(String label1) {
        addIRLine("br label %%%s", label1);
    }

    public String phi(String dstReg, String srcRegOrVal1, String srcRegOrVal2,
                      String label1, String label2) {
        addIRLine("%s = phi i1 [%s, %%%s], [%s, %%%s]", dstReg, srcRegOrVal1, label1, srcRegOrVal2, label2);
        return dstReg;
    }

    public String clazz(String className, List<String> types) {
        addIR("%%class.%s = type { ", className);
        addIR("%%vtable.%s*", className);
        if (types.size() != 0) {
            addIR(", ");
        }
        for (int i = 0; i < types.size(); i++) {
            String type = types.get(i);
            addIR("%s", getIRType(type));
            if (i != types.size() - 1) {
                addIR(", ");
            }
        }
        addIRLine(" }");
        return getIRType(className);
    }

    public String newConstruct(String tmpReg1, String dstReg, String tmpReg2, Class clazz) {
        int numElements = clazz.getFields().size();
        int maxSize = 0;
        for (Map.Entry<String, String> entry : clazz.getFields().entrySet()) {
            String irType = getIRType(entry.getValue());
            int irSize = getIRTypeSizeInBytes(irType);
            if (maxSize < irSize) {
                maxSize = irSize;
            }
        }
        addIRLine("%s = call noalias i8* @calloc(i64 1, i64 %d)", tmpReg1, maxSize * numElements);
        addIRLine("%s = bitcast i8* %s to %s", dstReg, tmpReg1, getIRType(clazz.getName()));
        addIRLine("%s = getelementptr inbounds %%class.%s, %%class.%s* %s, i32 0, i32 0", tmpReg2, clazz.getName(), clazz.getName(), dstReg);
        addIRLine("store %%vtable.%s* @%sVTable, %%vtable.%s** %s, align 8", clazz.getName(), clazz.getName(), clazz.getName(), tmpReg2);
        return dstReg;
    }

    public String methodCall(String dstReg, String methodName, String returnType, List<Pair<String, String>> args) {
        addIR("%s = call %s @%s(", dstReg, getIRType(returnType), methodName);
        int i = 0;
        for (Pair<String, String> arg : args) {
            addIR("%s %s", getIRType(arg.b), arg.a);
            i++;
            if (i != args.size()) {
                addIR(", ");
            }
        }
        addIRLine(")");
        return dstReg;
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

    private void addIR(String ir, Object... args) {
        outputIR.append(String.format(ir, args));
    }

    @Override
    public String toString() {
        return outputIR.toString();
    }

    public String getClassElement(String tmpReg, String dstReg, String resultType, String classType, String classReg, int index) {
        String classPointer = getIRType(classType);
        classType = classPointer.substring(0, classPointer.length() - 1);
        addIRLine("%s = getelementptr inbounds %s, %s %s, i32 0, i32 %d", tmpReg, classType, classPointer, classReg, index);
        return load(dstReg, resultType, tmpReg);
    }

    public void setClassElement(String tmpReg, String srcReg, String resultType, String classType, String classReg, int index) {
        String classPointer = getIRType(classType);
        classType = classPointer.substring(0, classPointer.length() - 1);
        addIRLine("%s = getelementptr inbounds %s, %s %s, i32 0, i32 %d", tmpReg, classType, classPointer, classReg, index);
        store(tmpReg, resultType, srcReg);
    }

    public String cast(String dstReg, String srcReg, String toType, String fromType) {
        toType = getIRType(toType);
        fromType = getIRType(fromType);
        addIRLine("%s = bitcast %s %s to %s", dstReg, fromType, srcReg, toType);
        return dstReg;
    }
}
