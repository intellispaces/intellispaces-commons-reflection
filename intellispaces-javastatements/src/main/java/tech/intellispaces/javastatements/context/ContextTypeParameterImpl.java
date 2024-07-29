package tech.intellispaces.javastatements.context;

import tech.intellispaces.javastatements.statement.reference.NamedReference;
import tech.intellispaces.javastatements.statement.reference.NotPrimitiveTypeReference;

class ContextTypeParameterImpl implements ContextTypeParameter {
  private final NamedReference namedType;
  private final NotPrimitiveTypeReference actualType;

  ContextTypeParameterImpl(NamedReference namedType, NotPrimitiveTypeReference actualType) {
    this.namedType = namedType;
    this.actualType = actualType;
  }

  @Override
  public NamedReference namedType() {
    return namedType;
  }

  @Override
  public NotPrimitiveTypeReference actualType() {
    return actualType;
  }
}
