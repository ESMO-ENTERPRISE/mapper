package com.esmo.mapper.processor.data.generator.method;

import com.esmo.mapper.processor.data.confwrappers.FieldConfigurationResolver;
import com.esmo.mapper.processor.data.confwrappers.FieldMappingData;
import com.esmo.mapper.processor.data.confwrappers.FieldValueAccessData;
import com.esmo.mapper.processor.data.constructors.TypeConstructorInfo;
import com.esmo.mapper.processor.data.MapperClassInfo;
import com.esmo.mapper.processor.data.MethodCallApi;
import com.esmo.mapper.processor.data.TypeWithVariableInfo;
import com.esmo.mapper.processor.data.keys.MethodConfigKey;
import com.esmo.mapper.processor.data.mapi.MethodApiFullSyntax;
import com.esmo.mapper.processor.data.mapi.MethodApiKey;
import com.esmo.mapper.processor.sourcewriter.SourceGeneratorContext;
import com.esmo.mapper.processor.utils.ElementUtils;
import com.esmo.mapper.processor.utils.NameUtils;
import com.esmo.mapper.processor.utils.commons.StringUtils;
import com.esmo.mapper.processor.data.TypeInfo;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.util.*;

public class SimpleMethodApi_CopyField_SourceInfo extends EmptyMethodSourceInfo {

	protected String bodyError = null;
	public SimpleMethodApi_CopyField_SourceInfo(MapperClassInfo ownerClassInfo, MethodApiFullSyntax methodApiParams) {
		super(ownerClassInfo, methodApiParams);
	}

	protected Map<MethodConfigKey, List<FieldConfigurationResolver.ResolvedTransformation>> analyzedDataMap = new HashMap<>();
	protected SortedSet<String> allKeys = new TreeSet<>();

	@Override
	public boolean hasMultipleVariants(ProcessingEnvironment processingEnv) {
		return this.analyzedDataMap.size() > 1;
	}

	@Override
	protected void analyzeAndGenerateDependMethods(ProcessingEnvironment processingEnv, MethodConfigKey forMethodConfig) {
		if (this.analyzedDataMap.containsKey(forMethodConfig)) {
			return;
		}

		// if this is default call and default call is in analyzed data, we can stop immediate
		if (!forMethodConfig.isWithCustomConfig()) {
			for (MethodConfigKey methodConfigKey : this.analyzedDataMap.keySet()) {
				if (!methodConfigKey.isWithCustomConfig()) {
					return;
				}
			}
		}


		// remember Source / Destination
		List<TypeWithVariableInfo> requiredParams = methodApiFullSyntax.getRequiredParams();
		TypeMirror typeFrom = requiredParams.get(0).getVariableType().getType(null);
		TypeMirror typeTo = requiredParams.get(1).getVariableType().getType(null);

		if (!ElementUtils.hasDefaultConstructor(processingEnv, typeTo)) {

			MethodApiKey constructorApiKey = new MethodApiKey(requiredParams.get(1).getVariableType(), Collections.emptyList());
			MethodCallApi methodCallApi = ownerClassInfo.findMethodApiToCall(processingEnv, constructorApiKey, null /*TODO: This constructor has to be created during analyzes */);
			if (methodCallApi==null) {
//				bodyError = "Default public constructor is not found!";
//				return;
			}
		}


		/////////////////////////
		// 1) Collect all information ...
		FieldConfigurationResolver resolver = new FieldConfigurationResolver(processingEnv, ownerClassInfo, forMethodConfig);
		List<FieldConfigurationResolver.ResolvedTransformation> transformGroups = resolver.findTransformationGroups(processingEnv, typeFrom, typeTo);
		this.analyzedDataMap.put(forMethodConfig, transformGroups);
		if (this.analyzedDataMap.size()>1) {
			ownerClassInfo.getFeatures().setRequiredInputWithMethodId(true);
		}

		/////////////////////////
		// 2) Check missing transformation methods ...
		for (FieldConfigurationResolver.ResolvedTransformation group : transformGroups) {
			registerInports(group.getPathFrom());
			registerInports(group.getPathTo());

			// Check transformation types
			for (FieldMappingData fieldMapping : group.fieldMappingData) {
				if (fieldMapping == null) continue;
				if (fieldMapping.getSrc() == null) continue;
				if (fieldMapping.getDst() == null) continue;

				TypeMirror sourceType = fieldMapping.getSrc().getTypeOfGetter();
				TypeMirror destinationType = fieldMapping.getDst().getTypeOfSetter();
				if (sourceType == null) continue;
				if (destinationType == null) continue;

				// check for same type and if its primitive type
				if (!fieldMapping.isWithoutProblemOrNotIgnored()) continue;


				// TODO: Complete this later
				if (StringUtils.isNotEmpty(fieldMapping.getMethodNameRequired())) {
					processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "@FieldMapping: methodNameS2D or methodNameD2S are not supported yet (this cofiguration is ignored).");
				}


				// Create or call method !!!
				fieldMapping.setMethodCallApi(findOrCreateOwnMethod(processingEnv, forMethodConfig, fieldMapping.getMethodNameRequired(), sourceType, destinationType));

				if (fieldMapping.getMethodCallApi() != null && fieldMapping.getMethodCallApi().getOutGeneratedMethod() != null && canAccept(fieldMapping, forMethodConfig)) {
					fieldMapping.getMethodCallApi().getOutGeneratedMethod().analyzeAndGenerateDependMethods(processingEnv, forMethodConfig, this);
				}

			}
		}
	}
	protected void registerInports(List<FieldValueAccessData> path) {
		if (path == null || path.isEmpty()) return;
		path.forEach(sourcesForImports::add);
	}

	@Override
	public boolean writeSourceCode(SourceGeneratorContext ctx) {
		if (bodyError!=null) {
			methodApiFullSyntax.writeMethodDeclaration(ctx);

			ctx.pw.print(" {");
			ctx.pw.levelSpaceUp();
			ctx.pw.print("\nthrow new IllegalStateException(\"" + bodyError + "\");");
			ctx.pw.levelSpaceDown();
			ctx.pw.print("\n}");
			return true;
		}

		return super.writeSourceCode(ctx);
	}

	@Override
	protected void writeSourceCodeBody(SourceGeneratorContext ctx) {
		List<TypeWithVariableInfo> requiredParams = methodApiFullSyntax.getRequiredParams();
		TypeWithVariableInfo varSrc = requiredParams.get(0);

		String inputVarSrcName = varSrc.getVariableName();
		String inputVarDstName = varRet.getVariableName();
		this.usedNames.add(inputVarDstName);

		// Instance

		writeSourceInstanceCacheLoad(ctx, varSrc, varRet);
		if (this.methodApiFullSyntax.isGenerateReturnParamRequired()) {
			ctx.pw.print("\nif (" + varRet.getVariableName() + " == null) { \n\t");
			ctx.pw.print(varRet.getVariableName());
		}
		else {
			// Declare variable ...
			ctx.pw.print("\n");
			varRet.writeSourceCode(ctx, true, false);
			ctx.pw.print(" ");
		}
		ctx.pw.print(" = ");
		writeConstructor(ctx, varRet);
		ctx.pw.print(";\n");
		if (this.methodApiFullSyntax.isGenerateReturnParamRequired()) ctx.pw.print("}");

		// We have to register cache value earlier before starts copying fields
		writeSourceInstanceCacheRegister(ctx, varSrc, varRet);

		ctx.pw.print("\n// Copy Fields ");

		String methodContextValue = null;
		if (ownerClassInfo.getFeatures().isRequiredInputWithMethodId()) {
			methodContextValue = this.varCtxMethodId.getVariableName();
		}

		/////////////////////////
		// 2) Resolve dependend transformations ...
		Map<String, String> cacheOfFoundPaths = new HashMap<>();
		List<MethodConfigKey> methodConfigKeyList = new ArrayList<>(analyzedDataMap.keySet());
		Collections.sort(methodConfigKeyList, new Comparator<MethodConfigKey>() {
			private String getStringKey(MethodConfigKey o1) {
				StringBuilder sb = new StringBuilder();
				sb.append(hasMultipleVariants(ctx.processingEnv) ? "a" : "z");
				sb.append(o1.getForTopMethod());
				return sb.toString();
			}

			@Override
			public int compare(MethodConfigKey o1, MethodConfigKey o2) {
				return getStringKey(o1).compareTo(getStringKey(o2));
			}
		});
		boolean genElse = false;

		Collections.sort(methodConfigKeyList, new Comparator<MethodConfigKey>() {
			private String getOrderKey(MethodConfigKey o) {
				if (o.isWithCustomConfig()) return "0-"+o.getForTopMethod();
				return "1-"+o.getForTopMethod();
			}
			@Override
			public int compare(MethodConfigKey o1, MethodConfigKey o2) {
				return getOrderKey(o1).compareTo(getOrderKey(o2));
			}
		});
		for (MethodConfigKey methodConfigKey : methodConfigKeyList) {
			List<FieldConfigurationResolver.ResolvedTransformation> groups = analyzedDataMap.get(methodConfigKey);

			if (groups.isEmpty()) continue;

			if (methodContextValue != null) cacheOfFoundPaths.clear();

			if (methodContextValue != null) {
				if (hasMultipleVariants(ctx.processingEnv) && methodConfigKey.isWithCustomConfig()) {
					ctx.pw.print("\n\n// Copy Fields - for method custom configuration: ");
					ctx.pw.print(methodConfigKey.getForTopMethod());
				}
				else {
					ctx.pw.print("\n\n// Copy Fields - default configuration ");
				}

				ctx.pw.printNewLine();
				if (genElse) {
					ctx.pw.print("else ");
				}
				genElse = true;

				if (hasMultipleVariants(ctx.processingEnv) && methodConfigKey.isWithCustomConfig()) {
					ctx.pw.print("if (");
					ctx.pw.print(methodContextValue);
					ctx.pw.print(" == ");
					ctx.pw.print(methodConfigKey.getForTopMethod());
					ctx.pw.print(") ");
				}

				ctx.pw.print("{");
				ctx.pw.levelSpaceUp();
			}

			for (FieldConfigurationResolver.ResolvedTransformation group : groups) {
				//ctx.pw.printNewLine();

				String varSrcName = createSubPathForNestedObject(ctx, inputVarSrcName, group.getPathFrom(), cacheOfFoundPaths, false);
				String varDstName = createSubPathForNestedObject(ctx, inputVarDstName, group.getPathTo(), cacheOfFoundPaths, true);

				String checkNullCondition = null;
				if (!StringUtils.equals(varSrcName, inputVarSrcName)) {
					checkNullCondition =  varSrcName + " != null";
				}

				if (varSrcName==null || varDstName == null) {
					ctx.pw.print("\n//TODO: methodgenerator or/and destination object is not reachable!");
					checkNullCondition = null;
				}
				else if (checkNullCondition!=null) {
					ctx.pw.print("\nif (" + checkNullCondition + ") {");
					ctx.pw.levelSpaceUp();
				}
				for (FieldMappingData mappingData : group.fieldMappingData) {
					mappingData.writeSourceCode(ctx, this, methodConfigKey, varSrcName, varDstName);
				}
				if (checkNullCondition!=null) {
					ctx.pw.levelSpaceDown();
					ctx.pw.print("\n}");
				}
			}

			if (methodContextValue != null) {
				ctx.pw.levelSpaceDown();
				ctx.pw.print("\n}");
			}
		}
	}

	protected String createSubPathForNestedObject(SourceGeneratorContext ctx, String originalVariable, List<FieldValueAccessData> pathToVariable, Map<String, String> cacheOfFoundPaths, boolean canCreateObject) {
		if (pathToVariable == null || pathToVariable.isEmpty()) return originalVariable;

		StringBuilder sbPathKey = new StringBuilder();
		sbPathKey.append(originalVariable);
		boolean canBeNull = false;
		for (FieldValueAccessData fieldValueAccessData : pathToVariable) {
			sbPathKey.append(".");
			sbPathKey.append(fieldValueAccessData.getFieldName());
			originalVariable = _createSubPathForNestedObject(ctx, originalVariable, sbPathKey.toString(), fieldValueAccessData, cacheOfFoundPaths, canCreateObject, canBeNull);
			canBeNull = true;
		}
		return originalVariable;
	}

	private String _createSubPathForNestedObject(SourceGeneratorContext ctx, String parentVariable, String pathKey, FieldValueAccessData pathToVariable, Map<String, String> cacheOfFoundPaths, boolean canCreateObject, boolean canBeNull) {
		if (pathToVariable == null || StringUtils.isEmpty(pathToVariable.getFieldName())) return parentVariable;

		String variable = cacheOfFoundPaths.get(pathKey);

		if (variable==null) {
			variable = NameUtils.findBestName(cacheOfFoundPaths.keySet(), StringUtils.replace(pathKey, ".", "_"));
			cacheOfFoundPaths.put(pathKey, variable);

			TypeWithVariableInfo variableInfo = new TypeWithVariableInfo(variable, new TypeInfo(pathToVariable.getTypeOfGetter()));

			String sourceForGetter = pathToVariable.getSourceForGetter(parentVariable);
			String[] sourceForSetter = pathToVariable.getSourceForSetter(parentVariable);

			ctx.pw.print("\n");
			variableInfo.writeSourceCode(ctx);
			ctx.pw.print(" = ");
			if (!canCreateObject && canBeNull) {
				ctx.pw.print("(" + parentVariable + "==null) ? null : ");
			}
			ctx.pw.print(sourceForGetter);
			ctx.pw.print(";");

			if (canCreateObject) {
				// Test if (variable == null) { variable = new Variable(); ....)
				ctx.pw.print("\nif (");
				ctx.pw.print(variable);
				ctx.pw.print(" == null) {");

				// new instance
				ctx.pw.print("\n\t");
				ctx.pw.print(variable);
				ctx.pw.print(" = ");
				new TypeConstructorInfo(variableInfo.getVariableType(), false).writeSourceCode(ctx);
				ctx.pw.print(";");

				// set value
				ctx.pw.print("\n\t");
				ctx.pw.print(sourceForSetter[0]);
				ctx.pw.print(sourceForSetter[1]);
				ctx.pw.print(variable);
				ctx.pw.print(sourceForSetter[2]);
				ctx.pw.print(";");

				ctx.pw.print("\n}");
			}
		}

		return variable;
	}

	protected boolean canAccept(FieldMappingData fieldMapping, MethodConfigKey forMethodConfig) {
		if (fieldMapping==null) return false;
		if (!fieldMapping.isWithoutProblemOrNotIgnored()) return false;

		return true;
	}
}
