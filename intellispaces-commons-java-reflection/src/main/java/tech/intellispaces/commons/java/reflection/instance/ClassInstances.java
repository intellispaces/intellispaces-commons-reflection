package tech.intellispaces.commons.java.reflection.instance;

import tech.intellispaces.commons.java.reflection.customtype.Classes;
import tech.intellispaces.commons.java.reflection.customtype.CustomType;

public interface ClassInstances {

  static ClassInstance of(CustomType type) {
    return new ClassInstanceImpl(type);
  }

  static ClassInstance of(Class<?> aClass) {
    return new ClassInstanceImpl(Classes.of(aClass));
  }
}
