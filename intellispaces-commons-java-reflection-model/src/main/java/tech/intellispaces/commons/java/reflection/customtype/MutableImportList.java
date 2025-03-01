package tech.intellispaces.commons.java.reflection.customtype;

import java.util.List;

public interface MutableImportList {

  void addHidden(String canonicalName);

  void add(String canonicalName);

  void add(Class<?> aClass);

  void add(Class<?>... classes);

  String simpleNameOf(Class<?> aClass);

  String simpleNameOf(String canonicalName);

  String addAndGetSimpleName(String canonicalName);

  String addAndGetSimpleName(Class<?> aClass);

  List<String> getImports();
}
