package intellispaces.javastatements.type;

import intellispaces.javastatements.reference.TypeReference;

import java.util.List;

public interface Type<T> extends intellispaces.commons.type.Type<T> {

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
