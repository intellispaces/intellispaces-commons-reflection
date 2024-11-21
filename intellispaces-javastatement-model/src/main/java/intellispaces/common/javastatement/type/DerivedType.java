package intellispaces.common.javastatement.type;

import intellispaces.common.javastatement.reference.TypeReference;
import tech.intellispaces.entity.type.Type;

import java.util.List;

/**
 * The type associated with the type reference.</p>
 *
 * Not every type reference can have a describable type associated with it.
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
