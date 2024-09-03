package intellispaces.common.javastatements.type;

import intellispaces.common.exception.UnexpectedViolationException;
import intellispaces.common.javastatements.reference.TypeReference;
import intellispaces.common.javastatements.reference.CustomTypeReferences;

import java.util.List;

class TypeBaseOnReferenceImpl<T> implements Type<T> {
  private final TypeReference reference;

  TypeBaseOnReferenceImpl(TypeReference reference) {
    this.reference = reference;
  }

  @Override
  public TypeReference typeReference() {
    return reference;
  }

  @Override
  public TypeReference base() {
    if (reference.isCustomTypeReference()) {
      return CustomTypeReferences.get(reference.asCustomTypeReferenceOrElseThrow().targetType());
    } else {
      throw UnexpectedViolationException.withMessage("Unsupported reference type: {0}",
          reference.statementType().typename());
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public Class<T> baseClass() {
    if (reference.isCustomTypeReference()) {
      return (Class<T>) reference.asCustomTypeReferenceOrElseThrow().targetClass();
    } else {
      throw UnexpectedViolationException.withMessage("Unsupported reference type: {0}",
          reference.statementType().typename());
    }
  }

  @Override
  @SuppressWarnings("unchecked, rawtypes")
  public List<TypeReference> qualifiers() {
    if (reference.isCustomTypeReference()) {
      return (List) reference.asCustomTypeReferenceOrElseThrow().typeArguments();
    } else {
      throw UnexpectedViolationException.withMessage("Unsupported reference type: {0}",
          reference.statementType().typename());
    }
  }
}
