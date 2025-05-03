package tech.intellispaces.jstatements.type;

import java.util.List;

import tech.intellispaces.commons.exception.UnexpectedExceptions;
import tech.intellispaces.commons.type.AbstractClassType;
import tech.intellispaces.jstatements.reference.CustomTypeReferences;
import tech.intellispaces.jstatements.reference.TypeReference;

class TypeBaseOnReferenceImpl<T> extends AbstractClassType<T> implements Type<T> {
  private final TypeReference reference;

  TypeBaseOnReferenceImpl(TypeReference reference) {
    this.reference = reference;
  }

  @Override
  public TypeReference typeReference() {
    return reference;
  }

  @Override
  public TypeReference baseTypeReference() {
    if (reference.isCustomTypeReference()) {
      return CustomTypeReferences.get(reference.asCustomTypeReferenceOrElseThrow().targetType());
    } else {
      throw UnexpectedExceptions.withMessage("Unsupported reference type: {0}",
          reference.statementType().typename());
    }
  }

  @Override
  @SuppressWarnings("unchecked, rawtypes")
  public List<TypeReference> qualifierTypeReferences() {
    if (reference.isCustomTypeReference()) {
      return (List) reference.asCustomTypeReferenceOrElseThrow().typeArguments();
    } else {
      throw UnexpectedExceptions.withMessage("Unsupported reference type: {0}",
          reference.statementType().typename());
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public Class<T> baseClass() {
    if (reference.isCustomTypeReference()) {
      return (Class<T>) reference.asCustomTypeReferenceOrElseThrow().targetClass();
    } else {
      throw UnexpectedExceptions.withMessage("Unsupported reference type: {0}",
        reference.statementType().typename());
    }
  }

  @Override
  @SuppressWarnings("unchecked, rawtypes")
  public List<tech.intellispaces.commons.type.Type<?>> qualifierTypes() {
    if (reference.isCustomTypeReference()) {
      return (List) reference.asCustomTypeReferenceOrElseThrow().typeArguments().stream()
        .map(TypeBaseOnReferenceImpl::new)
        .toList();
    } else {
      throw UnexpectedExceptions.withMessage("Unsupported reference type: {0}",
        reference.statementType().typename());
    }
  }
}
