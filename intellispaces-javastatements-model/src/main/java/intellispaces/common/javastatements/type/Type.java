package intellispaces.common.javastatements.type;

import intellispaces.common.javastatements.reference.TypeReference;

import java.util.List;

public interface Type<T> extends intellispaces.common.type.Type<T> {

  TypeReference typeReference();

  /**
   * Base type reference.
   */
  TypeReference base();

  /**
   * Base class.
   */
  Class<T> baseClass();

  /**
   * Type qualifier references (optional).
   */
  List<TypeReference> qualifiers();
}
