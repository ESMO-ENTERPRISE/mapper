package com.esmo.mapper.processor;

import com.esmo.mapper.common.annotations.Context;
import com.esmo.mapper.common.annotations.EsMapperGenerated;
import com.esmo.mapper.common.annotations.Return;
import com.esmo.mapper.common.utils.MapperRunCtxData;
import com.esmo.mapper.common.utils.MapperRunCtxDataHolder;
import com.esmo.mapper.common.utils.cache.InstanceCacheValue;
import com.esmo.mapper.processor.data.TypeInfo;
import com.esmo.mapper.processor.data.TypeWithVariableInfo;
import com.esmo.mapper.processor.data.AnnotationsInfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

abstract public class Constants {
    // my annotation types
    static final public TypeInfo annotationContext = new TypeInfo(Context.class);
    static final public TypeInfo annotationReturn = new TypeInfo(Return.class);
    static final public TypeInfo annotationMapperGenerated = new TypeInfo(EsMapperGenerated.class);

    // java annotations
    static final public TypeInfo annotationOverride = new TypeInfo(Override.class);

    static final public AnnotationsInfo createAnnotationGenerated() {
        AnnotationsInfo val = new AnnotationsInfo();
        val.getOrAddAnnotation(annotationMapperGenerated)
                .withStringValue(AnnotationEsMapperProcessor.class.getCanonicalName())
                .withKeyAndStringValue("date", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
        ;
        return val;
    }


    // Spring & CDI
    static final public String annotationFieldSPRING = "org.springframework.beans.factory.annotation.Autowired";
    static final public String annotationFieldCDI = "javax.inject.Inject";

    static final public String springComponent = "org.springframework.stereotype.Component";
    static final public String springContext = "org.springframework.context.annotation.Scope";

    static final public String cdiComponent = "javax.inject.Named.class";
    static final public String cdiContextRequest = "javax.enterprise.context.RequestScoped.class";
    static final public String cdiContextSession = "javax.enterprise.context.SessionScoped.class";
    static final public String cdiContextApplication = "javax.enterprise.context.ApplicationScoped.class";
    static final public String cdiContextSingleton = "javax.inject.Singleton.class";

    static final public TypeInfo typeInstanceCacheValue = new TypeInfo(InstanceCacheValue.class);
    static final public TypeInfo typeMapperRunCtxData = new TypeInfo(MapperRunCtxData.class);
    static final public TypeInfo typeMapperRunCtxDataHolder = new TypeInfo(MapperRunCtxDataHolder.class);

    static final public TypeWithVariableInfo methodParamInfo_ctxForMethodId = new TypeWithVariableInfo("confId", new TypeInfo(int.class), Context.esMapperConfig, false);
    static final public TypeWithVariableInfo methodParamInfo_ctxForRunData = new TypeWithVariableInfo("ctx", typeMapperRunCtxData, Context.esMapperContext, false);
}
