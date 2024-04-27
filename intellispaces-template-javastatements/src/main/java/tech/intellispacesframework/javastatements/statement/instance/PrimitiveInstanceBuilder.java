package tech.intellispacesframework.javastatements.statement.instance;

import tech.intellispacesframework.javastatements.statement.reference.PrimitiveTypeReference;

public interface PrimitiveInstanceBuilder {

  static PrimitiveInstance build(Object value, PrimitiveTypeReference type) {
    return new PrimitiveInstanceImpl(value, type);
  }
}
