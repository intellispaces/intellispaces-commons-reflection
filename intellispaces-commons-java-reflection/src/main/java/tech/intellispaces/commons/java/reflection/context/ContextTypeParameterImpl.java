package tech.intellispaces.commons.java.reflection.context;

import tech.intellispaces.commons.java.reflection.reference.NamedReference;
import tech.intellispaces.commons.java.reflection.reference.NotPrimitiveReference;

class ContextTypeParameterImpl implements ContextTypeParameter {
  private final NamedReference namedType;
  private final NotPrimitiveReference actualType;

  ContextTypeParameterImpl(NamedReference namedType, NotPrimitiveReference actualType) {
    this.namedType = namedType;
    this.actualType = actualType;
  }

  @Override
  public NamedReference namedType() {
    return namedType;
  }

  @Override
  public NotPrimitiveReference actualType() {
    return actualType;
  }
}
