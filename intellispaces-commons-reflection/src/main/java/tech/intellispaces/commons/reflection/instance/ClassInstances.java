package tech.intellispaces.commons.reflection.instance;

import tech.intellispaces.commons.reflection.customtype.Classes;
import tech.intellispaces.commons.reflection.customtype.CustomType;

public interface ClassInstances {

  static ClassInstance of(CustomType type) {
    return new ClassInstanceImpl(type);
  }

  static ClassInstance of(Class<?> aClass) {
    return new ClassInstanceImpl(Classes.of(aClass));
  }
}
