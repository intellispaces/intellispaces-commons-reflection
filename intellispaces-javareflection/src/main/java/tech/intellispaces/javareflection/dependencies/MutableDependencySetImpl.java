package tech.intellispaces.javareflection.dependencies;

import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

import tech.intellispaces.commons.collection.ArraysFunctions;
import tech.intellispaces.commons.exception.UnexpectedExceptions;
import tech.intellispaces.commons.type.ClassFunctions;
import tech.intellispaces.commons.type.ClassNameFunctions;
import tech.intellispaces.commons.type.PrimitiveFunctions;

class MutableDependencySetImpl implements MutableDependencySet {
  private final boolean excludeJavaLangClasses;
  private final Supplier<String> currentClassNameSupplier;
  private final HashMap<String, String> imports = new HashMap<>();
  private String currentClassName;
  private String currentClassSimpleName;
  private String currentClassPackageName;

  public MutableDependencySetImpl(boolean excludeJavaLangClasses, String currentClassName) {
    this.excludeJavaLangClasses = excludeJavaLangClasses;
    this.currentClassNameSupplier = () -> currentClassName;
  }

  public MutableDependencySetImpl(boolean excludeJavaLangClasses, Supplier<String> currentClassNameSupplier) {
    this.excludeJavaLangClasses = excludeJavaLangClasses;
    this.currentClassNameSupplier = currentClassNameSupplier;
  }

  @Override
  public void add(String canonicalName) {
    String simpleName = ClassNameFunctions.getSimpleName(canonicalName);
    imports.putIfAbsent(simpleName, canonicalName);
  }

  @Override
  public void add(Class<?> aClass) {
    add(aClass.getName());
  }

  @Override
  public void add(Class<?>... classes) {
    ArraysFunctions.foreach(classes, this::add);
  }

  @Override
  public String simpleNameOf(Class<?> aClass) {
    return simpleNameOf(aClass.getCanonicalName());
  }

  @Override
  public String simpleNameOf(String canonicalName) {
    if (!excludeJavaLangClasses && ClassFunctions.isLanguageClass(canonicalName)) {
      return canonicalName;
    }

    String simpleName = ClassNameFunctions.getSimpleName(canonicalName);
    if (simpleName.equals(currentClassSimpleName())) {
      if (canonicalName.equals(currentClassName())) {
        return simpleName;
      } else {
        return canonicalName;
      }
    }
    String importedCanonicalName = imports.get(simpleName);
    if (importedCanonicalName == null) {
      throw UnexpectedExceptions.withMessage("Class {0} is missing from list of imported classes", canonicalName);
    }
    if (canonicalName.equals(importedCanonicalName)) {
      return simpleName;
    }
    return canonicalName;
  }

  @Override
  public String addAndGetSimpleName(String canonicalName) {
    add(canonicalName);
    return simpleNameOf(canonicalName);
  }

  @Override
  public String addAndGetSimpleName(Class<?> aClass) {
    add(aClass);
    return simpleNameOf(aClass);
  }

  @Override
  public List<String> getImports() {
    return imports.values().stream()
        .filter(className -> !ClassNameFunctions.getSimpleName(className).equals(currentClassSimpleName()))
        .filter(className -> !ClassNameFunctions.getPackageName(className).equals(currentClassPackageName()))
        .filter(className -> !ClassFunctions.isLanguageClass(className))
        .filter(className -> !PrimitiveFunctions.isPrimitiveTypename(className))
        .sorted()
        .toList();
  }

  private String currentClassName() {
    if (currentClassName == null) {
      currentClassName = currentClassNameSupplier.get();
    }
    return currentClassName;
  }

  private String currentClassSimpleName() {
    if (currentClassSimpleName == null) {
      currentClassSimpleName = ClassNameFunctions.getSimpleName(currentClassNameSupplier.get());
    }
    return currentClassSimpleName;
  }

  private String currentClassPackageName() {
    if (currentClassPackageName == null) {
      currentClassPackageName = ClassNameFunctions.getPackageName(currentClassNameSupplier.get());
    }
    return currentClassPackageName;
  }
}
