package intellispaces.javastatements.context;

import intellispaces.javastatements.statement.reference.NamedTypeReference;
import intellispaces.javastatements.statement.reference.NonPrimitiveTypeReference;

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
