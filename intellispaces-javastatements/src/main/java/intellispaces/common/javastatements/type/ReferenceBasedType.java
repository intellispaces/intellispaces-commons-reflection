package intellispaces.common.javastatements.type;

import intellispaces.common.exception.UnexpectedViolationException;
import intellispaces.common.javastatements.reference.TypeReference;

import java.util.List;

class ReferenceBasedType<T> implements Type<T> {
  private final TypeReference base;
  private final List<TypeReference> qualifiers;

  ReferenceBasedType(TypeReference base, List<TypeReference> qualifiers) {
    this.base = base;
    this.qualifiers = qualifiers;
  }

  @Override
  public TypeReference typeReference() {
    return null;
  }

  @Override
  public TypeReference base() {
    return base;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Class<T> baseClass() {
    if (base.isCustomTypeReference()) {
      return (Class<T>) base.asCustomTypeReferenceOrElseThrow().targetClass();
    } else {
      throw UnexpectedViolationException.withMessage("Unsupported reference type: {0}",
          base.statementType().typename());
    }
  }

  @Override
  public List<TypeReference> qualifiers() {
    return qualifiers;
  }
}
