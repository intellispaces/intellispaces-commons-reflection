package tech.intellispaces.commons.java.reflection.type;

import tech.intellispaces.commons.type.Type;
import tech.intellispaces.commons.java.reflection.reference.TypeReference;

import java.util.List;

/**
 * The type associated with the type reference.</p>
 *
 * @param <T> associated type.
 */
public interface DerivedType<T> extends Type<T> {

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
