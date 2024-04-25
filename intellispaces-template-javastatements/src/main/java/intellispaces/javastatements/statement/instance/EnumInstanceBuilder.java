package intellispaces.javastatements.statement.instance;

import intellispaces.javastatements.statement.custom.EnumStatement;

public interface EnumInstanceBuilder {

  static EnumInstance build(EnumStatement type, String name) {
    return new EnumInstanceImpl(type, name);
  }
}
