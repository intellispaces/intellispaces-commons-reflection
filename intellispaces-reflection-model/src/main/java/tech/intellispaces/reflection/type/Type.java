package tech.intellispaces.reflection.type;

import tech.intellispaces.reflection.reference.TypeReference;

import java.util.List;

/**
 * The type associated with the type reference.</p>
 *
 * @param <T> associated type.
 */
public interface Type<T> extends tech.intellispaces.commons.type.Type<T> {

  /**
   * The type reference from which the type was derived.
   */
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
