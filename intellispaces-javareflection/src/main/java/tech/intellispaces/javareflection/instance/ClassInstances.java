package tech.intellispaces.javareflection.instance;

import tech.intellispaces.javareflection.customtype.Classes;
import tech.intellispaces.javareflection.customtype.CustomType;

public interface ClassInstances {

  static ClassInstance of(CustomType type) {
    return new ClassInstanceImpl(type);
  }

  static ClassInstance of(Class<?> aClass) {
    return new ClassInstanceImpl(Classes.of(aClass));
  }
}
