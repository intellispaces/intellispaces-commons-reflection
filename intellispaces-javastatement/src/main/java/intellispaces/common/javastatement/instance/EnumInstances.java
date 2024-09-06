package intellispaces.common.javastatement.instance;

import intellispaces.common.javastatement.customtype.EnumType;

public interface EnumInstances {

  static EnumInstance of(EnumType type, String name) {
    return new EnumInstanceImpl(type, name);
  }
}
