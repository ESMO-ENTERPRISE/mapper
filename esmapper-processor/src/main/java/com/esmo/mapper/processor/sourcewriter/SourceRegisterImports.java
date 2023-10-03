package com.esmo.mapper.processor.sourcewriter;

import javax.annotation.processing.ProcessingEnvironment;

public interface SourceRegisterImports {
	void registerImports(ProcessingEnvironment processingEnv, ImportsTypeDefinitions imports);

	static void runIfPresent(SourceRegisterImports value, ProcessingEnvironment processingEnv, ImportsTypeDefinitions imports) {
		if (value != null) value.registerImports(processingEnv, imports);
	}
}
