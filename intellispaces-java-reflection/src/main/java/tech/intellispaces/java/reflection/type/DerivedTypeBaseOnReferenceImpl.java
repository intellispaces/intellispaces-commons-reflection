package tech.intellispaces.java.reflection.type;

import tech.intellispaces.java.reflection.reference.CustomTypeReferences;
import tech.intellispaces.java.reflection.reference.TypeReference;
import tech.intellispaces.entity.exception.UnexpectedExceptions;
import tech.intellispaces.entity.type.AbstractType;

import java.util.List;

class DerivedTypeBaseOnReferenceImpl<T> extends AbstractType<T> implements DerivedType<T> {
  private final TypeReference reference;

  DerivedTypeBaseOnReferenceImpl(TypeReference reference) {
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
  public List<tech.intellispaces.entity.type.Type<?>> qualifierTypes() {
    if (reference.isCustomTypeReference()) {
      return (List) reference.asCustomTypeReferenceOrElseThrow().typeArguments().stream()
        .map(DerivedTypeBaseOnReferenceImpl::new)
        .toList();
    } else {
      throw UnexpectedExceptions.withMessage("Unsupported reference type: {0}",
        reference.statementType().typename());
    }
  }
}
