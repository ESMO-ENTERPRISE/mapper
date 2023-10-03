package com.esmo.mapper.processor.data.generator.row;

import com.esmo.mapper.processor.data.MapperClassInfo;
import com.esmo.mapper.processor.sourcewriter.SourceGeneratorContext;
import com.esmo.mapper.processor.utils.TypeUtils;
import com.esmo.mapper.processor.utils.annotations.AnnotationValueUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.type.TypeMirror;

public class NoRowValueTransformator extends AbstractRowValueTransformator {
	public static final NoRowValueTransformator instance = new NoRowValueTransformator();

	@Override
	boolean accept(ProcessingEnvironment processingEnv, MapperClassInfo ownerClassInfo, TypeMirror source, TypeMirror destination) {
		if (!TypeUtils.isSameType(processingEnv,source, destination)) return false;

		if (TypeUtils.isKnownImmutableType(processingEnv, source)) return true;

		// Check configuration immutable type
		if (AnnotationValueUtils.isConfiguredAsImmutableType(processingEnv, ownerClassInfo, destination)) return true;

		return false;
	}

	@Override
	public String generateRowTransform(SourceGeneratorContext ctx, TypeMirror source, TypeMirror destination, String varValue) {
		return varValue;
	}
}
