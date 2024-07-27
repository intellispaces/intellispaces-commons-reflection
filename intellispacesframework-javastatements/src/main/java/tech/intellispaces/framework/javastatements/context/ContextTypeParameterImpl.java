package tech.intellispaces.framework.javastatements.context;

import tech.intellispaces.framework.javastatements.statement.type.NamedType;
import tech.intellispaces.framework.javastatements.statement.type.NonPrimitiveType;

class ContextTypeParameterImpl implements ContextTypeParameter {
  private final NamedType namedType;
  private final NonPrimitiveType actualType;

  ContextTypeParameterImpl(NamedType namedType, NonPrimitiveType actualType) {
    this.namedType = namedType;
    this.actualType = actualType;
  }

  @Override
  public NamedType namedType() {
    return namedType;
  }

  @Override
  public NonPrimitiveType actualType() {
    return actualType;
  }
}
