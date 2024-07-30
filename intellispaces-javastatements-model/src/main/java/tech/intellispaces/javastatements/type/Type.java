package tech.intellispaces.javastatements.type;

import tech.intellispaces.javastatements.reference.TypeReference;

import java.util.List;

public interface Type<T> {

  /**
   * Base type.
   */
  TypeReference baseType();

  /**
   * Base class.
   */
  Class<?> baseClass();

  /**
   * Type qualifiers (optional).
   */
  List<TypeReference> qualifiers();
}
