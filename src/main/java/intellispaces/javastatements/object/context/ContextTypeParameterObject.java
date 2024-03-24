package intellispaces.javastatements.object.context;

import intellispaces.javastatements.model.context.ContextTypeParameter;
import intellispaces.javastatements.model.reference.NamedTypeReference;
import intellispaces.javastatements.model.reference.NonPrimitiveTypeReference;

class ContextTypeParameterObject implements ContextTypeParameter {
  private final NamedTypeReference type;
  private final NonPrimitiveTypeReference actualType;

  ContextTypeParameterObject(NamedTypeReference type, NonPrimitiveTypeReference actualType) {
    this.type = type;
    this.actualType = actualType;
  }

  @Override
  public NamedTypeReference type() {
    return type;
  }

  @Override
  public NonPrimitiveTypeReference actualType() {
    return actualType;
  }
}
