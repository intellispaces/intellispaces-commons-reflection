package tech.intellispaces.javastatements.type;

import tech.intellispaces.javastatements.reference.TypeReference;

import java.util.List;

public interface Type<T> extends tech.intellispaces.commons.type.Type<T> {

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
