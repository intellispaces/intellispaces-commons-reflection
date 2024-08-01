package tech.intellispaces.javastatements.type;

import tech.intellispaces.commons.exception.UnexpectedViolationException;
import tech.intellispaces.javastatements.reference.CustomTypeReference;
import tech.intellispaces.javastatements.reference.CustomTypeReferences;
import tech.intellispaces.javastatements.reference.TypeReference;

public interface TypeFunctions {

  static <T> Type<T> get(TypeReference typeReference) {
    if (typeReference.isCustomTypeReference()) {
      CustomTypeReference ctr = typeReference.asCustomTypeReferenceOrElseThrow();
      return new TypeImpl<>(CustomTypeReferences.get(ctr.targetType()), ctr.typeArguments());
    }
    throw UnexpectedViolationException.withMessage("Unsupported reference type: {}",
        typeReference.statementType().typename());
  }
}
