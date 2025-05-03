package tech.intellispaces.jstatements.instance;

import tech.intellispaces.jstatements.customtype.Classes;
import tech.intellispaces.jstatements.customtype.CustomType;

public interface ClassInstances {

  static ClassInstance of(CustomType type) {
    return new ClassInstanceImpl(type);
  }

  static ClassInstance of(Class<?> aClass) {
    return new ClassInstanceImpl(Classes.of(aClass));
  }
}
