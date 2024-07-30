package tech.intellispaces.javastatements.type;

import tech.intellispaces.commons.exception.UnexpectedViolationException;
import tech.intellispaces.javastatements.reference.TypeReference;

import java.util.List;

class TypeImpl<T> implements Type<T> {
  private final TypeReference baseType;
  private final List<TypeReference> qualifiers;

  @SuppressWarnings("unchecked")
  public TypeImpl(TypeReference baseType, List<? extends TypeReference> qualifiers) {
    this.baseType = baseType;
    this.qualifiers = (List<TypeReference>) qualifiers;
  }

  @Override
  public TypeReference baseType() {
    return baseType;
  }

  @Override
  public Class<?> baseClass() {
    if (baseType.isCustomTypeReference()) {
      return baseType.asCustomTypeReferenceConfidently().targetClass();
    }
    throw UnexpectedViolationException.withMessage("Not implemented");
  }

  @Override
  public List<TypeReference> qualifiers() {
    return qualifiers;
  }
}
