package com.esmo.mapper.processor.sourcewriter;

import com.esmo.mapper.processor.utils.TypeUtils;
import com.esmo.mapper.processor.utils.commons.StringUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImportsTypeDefinitions implements SourceGenerator {
	protected final Map<String, String> realNames = new LinkedHashMap<>();
	protected final Set<String> imports = new TreeSet<>();
	protected final Map<String, Element> dependOnElements = new LinkedHashMap<>();
	protected final String destPackage;

	public ImportsTypeDefinitions(TypeElement forClass) {
		this(TypeUtils.findPackageName(forClass.asType()));
	}
	protected ImportsTypeDefinitions(String destPackage) {
		this.destPackage = destPackage;
	}


	private static Pattern classNamePattern = Pattern.compile("[a-zA-Z0-9._]+");
	public void registerImports(ProcessingEnvironment processingEnv, TypeMirror type) {
		resolveType(processingEnv, type, true);
	}
	private String resolveType(ProcessingEnvironment processingEnv, TypeMirror type, boolean create) {
		if (type == null || type.getKind() == TypeKind.VOID) return null;
		if (type.getKind().isPrimitive()) return type.toString();

		if (!(type instanceof DeclaredType)) {
			return type.toString();
		}

		return resolveType(processingEnv, type.toString(), create);
	}
	protected String resolveType(ProcessingEnvironment processingEnv, String rawValue, boolean create) {

		Matcher matcher = classNamePattern.matcher(rawValue);
		StringBuilder sbOut = new StringBuilder();
		int lastStart = 0;
		while (matcher.find()) {
			String before = StringUtils.substring(rawValue, lastStart, matcher.start());
			if (StringUtils.isNotEmpty(before)) {
				sbOut.append(before);
			}

			lastStart = matcher.end();

			String oneClassName = matcher.group();
			String shortenClassName = realNames.get(oneClassName);
			if (shortenClassName == null && create) {
				ResolveImportStatus ris = resolveImportStatus(processingEnv, oneClassName);
				if (ris!=null && ris.printName!=null && !realNames.containsValue(ris.printName)) {
					shortenClassName = ris.printName;
					realNames.put(oneClassName, shortenClassName);
					if (ris.importPath != null) imports.add(ris.importPath);
				}
			}

			sbOut.append(shortenClassName!=null ? shortenClassName : oneClassName);
		}

		if (lastStart>=0 && lastStart<rawValue.length()) {
			sbOut.append(StringUtils.substring(rawValue, lastStart));
		}

		return sbOut.toString();
	}

	protected static class ResolveImportStatus {
		String importPath;
		String printName;

		public ResolveImportStatus(String importPath, String printName) {
			this.importPath = importPath;
			this.printName = printName;
		}
	}

	protected ResolveImportStatus resolveImportStatus(ProcessingEnvironment processingEnv, String simpleName) {
		TypeMirror type = TypeUtils.convertToTypeMirror(processingEnv, simpleName);
		if (type == null) return null;

		DeclaredType topElementType = TypeUtils.findTopElementType(type);
		if (topElementType == null) return null;

		String packageName = TypeUtils.findPackageName(topElementType);
		if (packageName == null) return new ResolveImportStatus(null, simpleName);

		registerElements(simpleName, packageName, type);

		String shortName = StringUtils.substring(simpleName, packageName.length() + 1);
		if (packageName.length() == 0) {
			shortName = simpleName;
		}
		if (StringUtils.equals(packageName, "java.lang")) return new ResolveImportStatus(null, shortName);
		if (StringUtils.equals(packageName, this.destPackage)) return new ResolveImportStatus(null, shortName);

		String importName = topElementType.asElement().toString();
		return new ResolveImportStatus(importName, shortName);
	}

	protected void registerElements(String simpleName, String packageName, TypeMirror type) {
		if (StringUtils.startsWith(packageName, "java.")) return;
		if (StringUtils.startsWith(packageName, "javax.")) return;
		if (StringUtils.startsWith(packageName, "com.esmo.mapper.common.annotations")) return;
		if (StringUtils.startsWith(packageName, "com.esmo.mapper.common.utils")) return;

		if (type.getKind() != TypeKind.DECLARED) return;
		Element element = ((DeclaredType) type).asElement();

		if (element.getKind() == ElementKind.ANNOTATION_TYPE) return;
		dependOnElements.put(simpleName, element);
	}

	public static void main(String[] args) {
		String rawValue = "java.util.List<my.custom.MyCustom.SecondCustom>";
	}

	public String resolveType(TypeMirror type) {
		// No optimalization so far
		return resolveType(null, type, false);
	}

	public final Map<String, String> registerTypes(ProcessingEnvironment processingEnv, String... fullPath) {
		if (fullPath == null || fullPath.length==0) return Collections.emptyMap();

		return new HashMap<>();
	}

	@Override
	public boolean writeSourceCode(SourceGeneratorContext ctx) {
		if (!imports.isEmpty()) {
			imports.forEach(imp -> ctx.pw.print("\nimport " + imp + ";"));
			ctx.pw.printNewLine();
		}

		return true;
	}
}
