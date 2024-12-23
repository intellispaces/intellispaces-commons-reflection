package tech.intellispaces.java.reflection.customtype;

import tech.intellispaces.general.collection.ArraysFunctions;
import tech.intellispaces.general.exception.UnexpectedExceptions;
import tech.intellispaces.general.type.ClassFunctions;
import tech.intellispaces.general.type.ClassNameFunctions;
import tech.intellispaces.general.type.PrimitiveFunctions;

import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

class MutableImportListImpl implements MutableImportList {
  private final Supplier<String> currentClassName;
  private final HashMap<String, String> imports = new HashMap<>();

  public MutableImportListImpl(String currentClassName) {
    this.currentClassName = () -> currentClassName;
  }

  public MutableImportListImpl(Supplier<String> currentClassName) {
    this.currentClassName = currentClassName;
  }

  @Override
  public void add(String canonicalName) {
    if (ClassFunctions.isLanguageClass(canonicalName)) {
      return;
    }
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
    String simpleName = ClassNameFunctions.getSimpleName(canonicalName);
    if (ClassFunctions.isLanguageClass(canonicalName)) {
      return simpleName;
    }
    if (simpleName.equals(currentClassSimpleName())) {
      return canonicalName;
    }

    String importedCanonicalName = imports.get(simpleName);
    if (importedCanonicalName == null) {
      throw UnexpectedExceptions.withMessage("Class {0} is missing from list of imported classes",
          canonicalName);
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

  private String currentClassSimpleName() {
    return ClassNameFunctions.getSimpleName(currentClassName.get());
  }

  private String currentClassPackageName() {
    return ClassNameFunctions.getPackageName(currentClassName.get());
  }
}
