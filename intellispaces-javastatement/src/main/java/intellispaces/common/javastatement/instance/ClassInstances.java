package intellispaces.common.javastatement.instance;

import intellispaces.common.javastatement.customtype.Classes;
import intellispaces.common.javastatement.customtype.CustomType;

public interface ClassInstances {

  static ClassInstance of(CustomType type) {
    return new ClassInstanceImpl(type);
  }

  static ClassInstance of(Class<?> aClass) {
    return new ClassInstanceImpl(Classes.of(aClass));
  }
}
