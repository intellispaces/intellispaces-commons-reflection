package tech.intellispaces.framework.javastatements.statement.reference;

import tech.intellispaces.framework.javastatements.statement.custom.CustomType;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * The custom type reference.
 */
public interface CustomTypeReference extends NonPrimitiveTypeReference, TypeBoundReference, ExceptionCompatibleTypeReference {

  /**
   * Target type.
   */
  CustomType targetType();

  /**
   * Actual arguments of type parameters.
   */
  List<NonPrimitiveTypeReference> typeArguments();

  CustomType effectiveTargetType();

  Map<String, NonPrimitiveTypeReference> typeArgumentMapping();

  String typeArgumentsDeclaration();

  String typeArgumentsDeclaration(Function<String, String> simpleNameMapper);

  @Override
  default boolean isArrayTypeReference() {
    return false;
  }

  @Override
  default boolean isCustomTypeReference() {
    return true;
  }

  @Override
  default boolean isNamedTypeReference() {
    return false;
  }

  @Override
  default boolean isWildcardTypeReference() {
    return false;
  }

  @Override
  default Optional<CustomTypeReference> asCustomTypeReference() {
    return Optional.of(this);
  }
}
