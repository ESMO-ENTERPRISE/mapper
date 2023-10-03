package com.esmo.mapper.processor.data.generator.method;

import com.esmo.mapper.processor.data.MapperClassInfo;
import com.esmo.mapper.processor.data.TypeWithVariableInfo;
import com.esmo.mapper.processor.data.keys.MethodConfigKey;
import com.esmo.mapper.processor.data.mapi.MethodApiFullSyntax;
import com.esmo.mapper.processor.sourcewriter.SourceGeneratorContext;

import javax.annotation.processing.ProcessingEnvironment;
import java.util.List;

public class EmptyMethodSourceInfo extends AbstractMethodSourceInfo {
	public EmptyMethodSourceInfo(MapperClassInfo ownerClassInfo, MethodApiFullSyntax methodApiParams) {
		super(ownerClassInfo, methodApiParams);
	}

	@Override
	protected void analyzeAndGenerateDependMethods(ProcessingEnvironment processingEnv, MethodConfigKey forMethodConfig) {
		//nothing todo
	}

	@Override
	protected void writeSourceCodeBody(SourceGeneratorContext ctx) {
		// return
		if (methodApiFullSyntax.getReturnType()!=null) {
			if (methodApiFullSyntax.isReturnLastParam()) {
				ctx.pw.print("\nreturn ");
				List<TypeWithVariableInfo> requiredParams = methodApiFullSyntax.getRequiredParams();
				ctx.pw.print(requiredParams.get(requiredParams.size()-1).getVariableName());
				ctx.pw.print(";");
			}
			else {
				// TODO: possible problem with primitive types
				ctx.pw.print("\nreturn null;");
			}
		}

	}

}
