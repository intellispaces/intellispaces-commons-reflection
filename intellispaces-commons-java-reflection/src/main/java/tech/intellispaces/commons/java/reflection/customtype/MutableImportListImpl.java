package tech.intellispaces.commons.java.reflection.customtype;

import tech.intellispaces.commons.base.collection.ArraysFunctions;
import tech.intellispaces.commons.base.exception.UnexpectedExceptions;
import tech.intellispaces.commons.base.type.ClassFunctions;
import tech.intellispaces.commons.base.type.ClassNameFunctions;
import tech.intellispaces.commons.base.type.PrimitiveFunctions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.Supplier;

class MutableImportListImpl implements MutableImportList {
  private final Supplier<String> currentClassName;
  private final HashMap<String, String> imports = new HashMap<>();
  private final HashMap<String, String> hiddenImports = new HashMap<>();

  public MutableImportListImpl(String currentClassName) {
    this.currentClassName = () -> currentClassName;
  }

  public MutableImportListImpl(Supplier<String> currentClassName) {
    this.currentClassName = currentClassName;
  }

  @Override
  public void addHidden(String canonicalName) {
    String simpleName = ClassNameFunctions.getSimpleName(canonicalName);
    hiddenImports.put(simpleName, canonicalName);
  }

  @Override
  public void add(String canonicalName) {
    if (ClassFunctions.isLanguageClass(canonicalName)) {
      return;
    }
    String simpleName = ClassNameFunctions.getSimpleName(canonicalName);
    String hiddenClassCanonicalName = hiddenImports.get(simpleName);
    String hiddenClassPackageName = (hiddenClassCanonicalName != null ? ClassNameFunctions.getPackageName(hiddenClassCanonicalName) : null);
    if (hiddenClassCanonicalName == null || !hiddenClassPackageName.equals(currentClassPackageName())) {
      imports.putIfAbsent(simpleName, canonicalName);
    }
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
    String packageName = ClassNameFunctions.getPackageName(canonicalName);
    String currentClassPackageName = currentClassPackageName();

    String hiddenClassCanonicalName = hiddenImports.get(simpleName);
    String hiddenClassPackageName = (hiddenClassCanonicalName != null ? ClassNameFunctions.getPackageName(hiddenClassCanonicalName) : null);
    if (hiddenClassCanonicalName != null && hiddenClassPackageName.equals(currentClassPackageName) && !canonicalName.equals(hiddenClassCanonicalName)) {
      return canonicalName;
    }

    if (ClassFunctions.isLanguageClass(canonicalName)) {
      return simpleName;
    }

    if (packageName.equals(currentClassPackageName)) {
      return simpleName;
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
