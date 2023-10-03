package com.esmo.mapper.processor.data;

import com.esmo.mapper.processor.sourcewriter.ImportsTypeDefinitions;
import com.esmo.mapper.processor.sourcewriter.SourceGenerator;
import com.esmo.mapper.processor.sourcewriter.SourceGeneratorContext;
import com.esmo.mapper.processor.sourcewriter.SourceRegisterImports;
import com.esmo.mapper.processor.utils.commons.StringUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import java.util.Optional;

public class FieldInfo implements SourceGenerator, SourceRegisterImports {
	final private TypeWithVariableInfo variable;

	public FieldInfo(TypeInfo type) {
		this.variable =  new TypeWithVariableInfo(type);
		this.variable.modifiers.add(Modifier.PROTECTED);
		this.variable.inlineMode=false;
	}
	public FieldInfo(String name, TypeInfo type) {
		this.variable =  new TypeWithVariableInfo(name, type);
		this.variable.modifiers.add(Modifier.PROTECTED);
	}

	public FieldInfo withInjections(AnnotationsInfo injections) {
		this.variable.withAnnotations(injections);
		this.variable.inlineMode=false;
		return this;
	}


	public String getName() {
		return variable.getVariableName();
	}


	private Optional<TypeInfo> customFieldInstance = null;
	protected TypeInfo getFieldTypeForInstance(ProcessingEnvironment processingEnv) {
		if (true) return null;

		return customFieldInstance.orElse(null);
	}

	@Override
	public boolean writeSourceCode(SourceGeneratorContext ctx) {
		variable.writeSourceCode(ctx);

		// If this is generated mapper - we can create instance of this
		TypeInfo typeForInstance = getFieldTypeForInstance(ctx.processingEnv);
		if (typeForInstance != null) {
			// TODO: Check how we can INITIALIZE it
			ctx.pw.print(" = new ");
			typeForInstance.writeSourceCode(ctx);
			ctx.pw.print("()");
		}
		ctx.pw.print(";");

		// Setter
		ctx.pw.print("\npublic void set");
		ctx.pw.print(StringUtils.capitalize(variable.getVariableName()));
		ctx.pw.print("(");
		variable.writeSourceCode(ctx, true, false, false);
		ctx.pw.print(") {");

		ctx.pw.print("\n\tthis.");
		ctx.pw.print(variable.getVariableName());
		ctx.pw.print(" = ");
		ctx.pw.print(variable.getVariableName());
		ctx.pw.print(";\n}");

		return true;
	}

	@Override
	public void registerImports(ProcessingEnvironment processingEnv, ImportsTypeDefinitions imports) {
		variable.registerImports(processingEnv, imports);
		SourceRegisterImports.runIfPresent(getFieldTypeForInstance(processingEnv), processingEnv, imports);
	}

}
