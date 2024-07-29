package tech.intellispaces.javastatements.type;

import tech.intellispaces.javastatements.statement.reference.TypeReference;

import java.util.List;

public interface Type<T> {

  /**
   * Base type.
   */
  TypeReference baseType();

  /**
   * Type qualifiers (optional).
   */
  List<TypeReference> qualifiers();
}
