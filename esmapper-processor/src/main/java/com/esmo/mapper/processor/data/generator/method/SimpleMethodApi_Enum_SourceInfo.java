package com.esmo.mapper.processor.data.generator.method;

import com.esmo.mapper.processor.data.MapperClassInfo;
import com.esmo.mapper.processor.data.TypeWithVariableInfo;
import com.esmo.mapper.processor.data.keys.MethodConfigKey;
import com.esmo.mapper.processor.data.mapi.MethodApiFullSyntax;
import com.esmo.mapper.processor.sourcewriter.SourceGeneratorContext;
import com.esmo.mapper.processor.utils.TypeUtils;
import com.esmo.mapper.processor.data.TypeInfo;

import javax.annotation.processing.ProcessingEnvironment;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SimpleMethodApi_Enum_SourceInfo extends EmptyMethodSourceInfo {

    public SimpleMethodApi_Enum_SourceInfo(MapperClassInfo ownerClassInfo, MethodApiFullSyntax methodApiParams) {
        super(ownerClassInfo, methodApiParams);
    }

    @Override
    protected void analyzeAndGenerateDependMethods(ProcessingEnvironment processingEnv, MethodConfigKey forMethodConfig) {
    }

    @Override
    protected void writeSourceCodeBodyReturn(SourceGeneratorContext ctx) {
        // nothing :)
    }

    @Override
    protected boolean canCallInterceptors() {
        return false;
    }


    @Override
    protected void writeSourceCodeBody(SourceGeneratorContext ctx) {
        List<TypeWithVariableInfo> requiredParams = methodApiFullSyntax.getRequiredParams();
        TypeWithVariableInfo varSrc = requiredParams.get(0);
        String inputVarSrcName = varSrc.getVariableName();
        String inputVarDstName = varRet.getVariableName();
        this.usedNames.add(inputVarDstName);


        // remember Source / Destination
        TypeInfo typeSrc = varSrc.getVariableType();
        TypeInfo typeRet = varRet.getVariableType();

        List<String> enumSrc = TypeUtils.getEnumValues(ctx.processingEnv, typeSrc);
        List<String> enumDst = TypeUtils.getEnumValues(ctx.processingEnv, typeRet);

        ctx.pw.print("\nswitch(");
        ctx.pw.print(inputVarSrcName);
        ctx.pw.print(") {");

        Set<String> allConstants = new TreeSet<>();
        allConstants.addAll(enumSrc);
        allConstants.addAll(enumDst);

        for (String constantName : allConstants) {
            String error = null;
            if (!enumDst.contains(constantName)) {
                error = "Missing in DESTINATION";
            } else if (!enumSrc.contains(constantName)) {
                error = "Missing in SOURCE";
            }
            writeOneEnumValues(ctx, typeRet, constantName, error);
        }
        ctx.pw.print("\n}");
        ctx.pw.print("\nreturn null;");
    }


    protected static void writeOneEnumValues(SourceGeneratorContext ctx, TypeInfo typeRet, String src, String errorText) {
        ctx.pw.print("\n\t");
        if (errorText != null) {
            ctx.pw.print("//todo: ");

            ctx.pw.print(errorText);
            ctx.pw.print(": ");
        }
        ctx.pw.print("case ");
        ctx.pw.print(src);
        ctx.pw.print(": return ");
        typeRet.writeSourceCode(ctx);
        ctx.pw.print(".");
        ctx.pw.print(src);
        ctx.pw.print(";");
    }
}
