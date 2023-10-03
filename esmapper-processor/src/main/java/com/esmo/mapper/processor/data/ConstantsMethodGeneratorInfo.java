package com.esmo.mapper.processor.data;

import com.esmo.mapper.processor.sourcewriter.ImportsTypeDefinitions;
import com.esmo.mapper.processor.sourcewriter.SourceGenerator;
import com.esmo.mapper.processor.sourcewriter.SourceGeneratorContext;
import com.esmo.mapper.processor.sourcewriter.SourceRegisterImports;
import com.esmo.mapper.processor.utils.commons.StringEscapeUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConstantsMethodGeneratorInfo implements SourceGenerator, SourceRegisterImports {

	//	for Every Constants: protected static final int {constanctsForTopMethods.key} = "{constanctsForTopMethods.value}".hashCode();
	final public Map<String, String> constanctsForTopMethods = new LinkedHashMap<>();

	public String registerTopMethod (ExecutableElement method, MapperClassInfo ownerClassInfo) {
		String constantFieldName = String.format("__constantMethod_%02d_%s", constanctsForTopMethods.size() + 1, method.getSimpleName());
		constanctsForTopMethods.put(constantFieldName, method.toString());
		return constantFieldName;
	}

	@Override
	public boolean writeSourceCode(SourceGeneratorContext ctx) {

		// static fields !!!
		for (Map.Entry<String, String> entry : constanctsForTopMethods.entrySet()) {
			ctx.pw.print("\nprotected static final int ");
			ctx.pw.print(entry.getKey());
			ctx.pw.print(" = ");
			ctx.pw.print("\"");
			ctx.pw.print(StringEscapeUtils.escapeJava(entry.getValue()));
			ctx.pw.print("\".hashCode();");
		}

		if (!constanctsForTopMethods.isEmpty())	ctx.pw.print("\n");

		return true;
	}

	@Override
	public void registerImports(ProcessingEnvironment processingEnv, ImportsTypeDefinitions imports) {
	}

}
