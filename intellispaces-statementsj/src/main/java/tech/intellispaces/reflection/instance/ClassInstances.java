package tech.intellispaces.statementsj.instance;

import tech.intellispaces.statementsj.customtype.Classes;
import tech.intellispaces.statementsj.customtype.CustomType;

public interface ClassInstances {

  static ClassInstance of(CustomType type) {
    return new ClassInstanceImpl(type);
  }

  static ClassInstance of(Class<?> aClass) {
    return new ClassInstanceImpl(Classes.of(aClass));
  }
}
