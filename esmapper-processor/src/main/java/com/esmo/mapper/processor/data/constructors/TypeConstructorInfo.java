package com.esmo.mapper.processor.data.constructors;

import com.esmo.mapper.processor.sourcewriter.ImportsTypeDefinitions;
import com.esmo.mapper.processor.sourcewriter.SourceGenerator;
import com.esmo.mapper.processor.sourcewriter.SourceGeneratorContext;
import com.esmo.mapper.processor.sourcewriter.SourceRegisterImports;
import com.esmo.mapper.processor.utils.ElementUtils;
import com.esmo.mapper.processor.utils.TypeUtils;
import com.esmo.mapper.processor.data.TypeInfo;

import javax.annotation.processing.ProcessingEnvironment;

public class TypeConstructorInfo implements SourceGenerator, SourceRegisterImports {
	final private TypeInfo typeOriginal;
	private TypeInfo typeConstructor = null;
	private boolean constructorReference;

	public TypeConstructorInfo(TypeInfo type, boolean constructorReference) {
		this.typeOriginal = type;
		this.constructorReference = constructorReference;

	}

	protected TypeInfo getTypeConstructor(ProcessingEnvironment processingEnv) {
		if (typeConstructor == null) {
			this.typeConstructor = TypeUtils.resolveConstructorType(processingEnv, typeOriginal);
		}
		return typeConstructor;
	}

	@Override
	public boolean writeSourceCode(SourceGeneratorContext ctx) {
		writeSourceCodeWithParams(ctx);
		return true;
	}
	public void writeSourceCodeWithParams(SourceGeneratorContext ctx, String... sourceAsParams) {
		TypeInfo typeConstructor = getTypeConstructor(ctx.processingEnv);

		// Todo - check Collections & Interfaces & Default Public Constructors !!!
		if (!ElementUtils.hasDefaultConstructor(ctx.processingEnv, typeOriginal.getType(ctx.processingEnv))) {
			ctx.pw.print("null /*NO DEFAULT CONSTRUCTOR*/");
			return;
		}


		boolean withParams = sourceAsParams!=null && sourceAsParams.length>0;
		boolean isArray = typeOriginal.isArray(ctx.processingEnv);

		if (constructorReference && !withParams && !isArray) {
			typeConstructor.writeSourceCode(ctx);
			ctx.pw.print("::new");
			return;
		}

		if (constructorReference) {
			ctx.pw.print("() -> ");
		}

		ctx.pw.print("new ");
		typeConstructor.writeSourceCode(ctx);
		ctx.pw.print(isArray ? "[" : "(");
		if (withParams) {
			for (int i = 0; i < sourceAsParams.length; i++) {
				if (i>0) ctx.pw.print(",");
				ctx.pw.print(sourceAsParams[i]);
			}
		}
		ctx.pw.print(isArray ? "]" : ")");
	}

	@Override
	public void registerImports(ProcessingEnvironment processingEnv, ImportsTypeDefinitions imports) {
		getTypeConstructor(processingEnv).registerImports(processingEnv, imports);
	}
}
