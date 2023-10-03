package com.esmo.mapper.processor.utils.annotations.data;

import com.esmo.mapper.common.annotations.enums.ConfigErrorReporting;
import com.esmo.mapper.processor.utils.annotations.data.fields.AnnotationFieldId;

public class AnnotationConfigGenerator {
	private AnnotationFieldId fieldId;
	private ConfigErrorReporting missingAsSource;
	private ConfigErrorReporting missingAsDestination;

	public AnnotationFieldId getFieldId() {
		return fieldId;
	}

	public void setFieldId(AnnotationFieldId fieldId) {
		this.fieldId = fieldId;
	}

	public ConfigErrorReporting getMissingAsSource() {
		return missingAsSource;
	}

	public void setMissingAsSource(ConfigErrorReporting missingAsSource) {
		this.missingAsSource = missingAsSource;
	}

	public ConfigErrorReporting getMissingAsDestination() {
		return missingAsDestination;
	}

	public void setMissingAsDestination(ConfigErrorReporting missingAsDestination) {
		this.missingAsDestination = missingAsDestination;
	}
}
