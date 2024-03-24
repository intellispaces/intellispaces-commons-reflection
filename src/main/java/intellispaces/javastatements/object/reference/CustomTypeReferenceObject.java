package intellispaces.javastatements.object.reference;

import intellispaces.javastatements.model.custom.CustomType;
import intellispaces.javastatements.model.reference.CustomTypeReference;
import intellispaces.javastatements.model.reference.NonPrimitiveTypeReference;

import java.util.List;

class CustomTypeReferenceObject extends AbstractTypeReference implements CustomTypeReference {
  private final CustomType targetType;
  private final List<NonPrimitiveTypeReference> typeArguments;

  CustomTypeReferenceObject(CustomType targetType, List<NonPrimitiveTypeReference> typeArguments) {
    super();
    this.targetType = targetType;
    this.typeArguments = typeArguments;
  }

  @Override
  public CustomType targetType() {
    return targetType;
  }

  @Override
  public List<NonPrimitiveTypeReference> typeArguments() {
    return typeArguments;
  }
}
