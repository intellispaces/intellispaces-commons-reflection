package tech.intellispaces.javastatements.type;

import tech.intellispaces.commons.exception.UnexpectedViolationException;
import tech.intellispaces.javastatements.reference.CustomTypeReference;
import tech.intellispaces.javastatements.reference.CustomTypeReferences;
import tech.intellispaces.javastatements.reference.TypeReference;

public interface TypeFunctions {

  static <T> Type<T> get(TypeReference typeReference) {
    if (typeReference.isCustomType()) {
      CustomTypeReference ctr = typeReference.asCustomTypeConfidently();
      return new TypeImpl<>(CustomTypeReferences.of(ctr.customType()), ctr.typeArguments());
    }
    throw UnexpectedViolationException.withMessage("Unsupported reference type: {}",
        typeReference.statementType().typename());
  }
}
