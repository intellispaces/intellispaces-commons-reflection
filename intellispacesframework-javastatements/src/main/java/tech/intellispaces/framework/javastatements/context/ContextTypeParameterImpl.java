package tech.intellispaces.framework.javastatements.context;

import tech.intellispaces.framework.javastatements.statement.reference.NamedTypeReference;
import tech.intellispaces.framework.javastatements.statement.reference.NonPrimitiveTypeReference;

class ContextTypeParameterImpl implements ContextTypeParameter {
  private final NamedTypeReference type;
  private final NonPrimitiveTypeReference actualType;

  public ContextTypeParameterImpl(NamedTypeReference type, NonPrimitiveTypeReference actualType) {
    this.type = type;
    this.actualType = actualType;
  }

  @Override
  public NamedTypeReference reference() {
    return type;
  }

  @Override
  public NonPrimitiveTypeReference type() {
    return actualType;
  }
}
