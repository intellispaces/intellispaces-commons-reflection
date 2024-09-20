package intellispaces.common.javastatement.type;

import intellispaces.common.javastatement.reference.TypeReference;

import java.util.List;

public interface Type<T> extends intellispaces.common.base.type.Type<T> {

  TypeReference typeReference();

  /**
   * Base type reference.
   */
  TypeReference baseTypeReference();

  /**
   * Type qualifier references.
   */
  List<TypeReference> qualifierTypeReferences();
}
