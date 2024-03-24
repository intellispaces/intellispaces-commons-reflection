package intellispaces.javastatements.model.reference;

import intellispaces.javastatements.model.StatementType;
import intellispaces.javastatements.object.StatementTypes;

import java.util.Optional;

/**
 * The wildcard type reference.
 */
public interface WildcardTypeReference extends NonPrimitiveTypeReference {

  /**
   * Extended bound.
   */
  Optional<TypeBoundReference> extendedBound();

  /**
   * Super bound.
   */
  Optional<TypeBoundReference> superBound();

  @Override
  default StatementType statementType() {
    return StatementTypes.WildcardTypeReference;
  }

  @Override
  default Optional<WildcardTypeReference> asWildcardTypeReference() {
    return Optional.of(this);
  }
}
