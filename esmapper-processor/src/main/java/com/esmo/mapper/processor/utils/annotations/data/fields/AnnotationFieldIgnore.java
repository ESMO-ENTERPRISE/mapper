package com.esmo.mapper.processor.utils.annotations.data.fields;

import com.esmo.mapper.common.annotations.enums.IgnoreType;

public class AnnotationFieldIgnore extends AnnotationFieldId {
	IgnoreType ignored;

	public IgnoreType getIgnored() {
		return ignored;
	}

	public void setIgnored(IgnoreType ignored) {
		this.ignored = ignored;
	}
}
