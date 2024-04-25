package intellispaces.javastatements.statement.instance;

import intellispaces.javastatements.statement.custom.CustomType;

public interface ClassInstanceBuilder {

  static ClassInstance build(CustomType type) {
    return new ClassInstanceImpl(type);
  }
}
