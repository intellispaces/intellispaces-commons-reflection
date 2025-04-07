package tech.intellispaces.reflection.reference;

import tech.intellispaces.reflection.customtype.CustomType;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * The custom type reference.
 */
public interface CustomTypeReference extends NotPrimitiveReference, ReferenceBound, ThrowableReference {

  /**
   * Target base type.
   */
  CustomType targetType();

  /**
   * Target base class.
   */
  Class<?> targetClass();

  /**
   * Actual arguments of type parameters.
   */
  List<NotPrimitiveReference> typeArguments();

  CustomType effectiveTargetType();

  Map<String, NotPrimitiveReference> typeArgumentMapping();

  String typeArgumentsDeclaration();

  String typeArgumentsDeclaration(Function<String, String> simpleNameMapper);

  @Override
  default boolean isArrayReference() {
    return false;
  }

  @Override
  default boolean isCustomTypeReference() {
    return true;
  }

  @Override
  default boolean isNamedReference() {
    return false;
  }

  @Override
  default boolean isWildcard() {
    return false;
  }

  @Override
  default Optional<CustomTypeReference> asCustomTypeReference() {
    return Optional.of(this);
  }
}
