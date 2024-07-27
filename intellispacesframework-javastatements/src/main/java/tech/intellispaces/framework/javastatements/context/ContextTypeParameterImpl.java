package tech.intellispaces.framework.javastatements.context;

import tech.intellispaces.framework.javastatements.statement.type.NamedType;
import tech.intellispaces.framework.javastatements.statement.type.NotPrimitiveType;

class ContextTypeParameterImpl implements ContextTypeParameter {
  private final NamedType namedType;
  private final NotPrimitiveType actualType;

  ContextTypeParameterImpl(NamedType namedType, NotPrimitiveType actualType) {
    this.namedType = namedType;
    this.actualType = actualType;
  }

  @Override
  public NamedType namedType() {
    return namedType;
  }

  @Override
  public NotPrimitiveType actualType() {
    return actualType;
  }
}
