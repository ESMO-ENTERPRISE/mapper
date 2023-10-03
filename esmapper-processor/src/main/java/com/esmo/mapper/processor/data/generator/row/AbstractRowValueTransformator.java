package com.esmo.mapper.processor.data.generator.row;

import com.esmo.mapper.processor.data.MapperClassInfo;
import com.esmo.mapper.processor.sourcewriter.SourceGeneratorContext;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.type.TypeMirror;

abstract public class AbstractRowValueTransformator {

	static AbstractRowValueTransformator[] rowFieldGenerators = new AbstractRowValueTransformator[] {
		NoRowValueTransformator.instance,
		DateRowValueTransformator.instance,
		SimpleRowValueTransformator.instance
	};

	abstract boolean accept(ProcessingEnvironment processingEnv, MapperClassInfo ownerClassInfo, TypeMirror source, TypeMirror destination);
	abstract public String generateRowTransform(SourceGeneratorContext ctx, TypeMirror source, TypeMirror destination, String varValue);

	public static AbstractRowValueTransformator findRowFieldGenerator(ProcessingEnvironment processingEnv, MapperClassInfo ownerClassInfo, TypeMirror sourceType, TypeMirror destinationType) {

		for (AbstractRowValueTransformator rowFieldGenerator : rowFieldGenerators) {
			try {
				if (rowFieldGenerator.accept(processingEnv, ownerClassInfo, sourceType, destinationType)) return rowFieldGenerator;
			}
			catch (Exception e) {/*not important*/}
		}

		return null;

	}
}
