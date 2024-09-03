package intellispaces.common.javastatements.instance;

import intellispaces.common.javastatements.customtype.EnumType;

public interface EnumInstances {

  static EnumInstance of(EnumType type, String name) {
    return new EnumInstanceImpl(type, name);
  }
}
