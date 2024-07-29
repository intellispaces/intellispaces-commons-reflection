package tech.intellispaces.javastatements.statement.reference;

import tech.intellispaces.javastatements.statement.customtype.CustomType;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * The custom type reference.
 */
public interface CustomTypeReference extends NotPrimitiveTypeReference, TypeReferenceBound, ThrowableTypeReference {

  /**
   * Base type.
   */
  CustomType customType();

  /**
   * Actual arguments of type parameters.
   */
  List<NotPrimitiveTypeReference> typeArguments();

  CustomType effectiveStatement();

  Map<String, NotPrimitiveTypeReference> typeArgumentMapping();

  String typeArgumentsDeclaration();

  String typeArgumentsDeclaration(Function<String, String> simpleNameMapper);

  @Override
  default boolean isArray() {
    return false;
  }

  @Override
  default boolean isCustomType() {
    return true;
  }

  @Override
  default boolean isNamed() {
    return false;
  }

  @Override
  default boolean isWildcard() {
    return false;
  }

  @Override
  default Optional<CustomTypeReference> asCustomType() {
    return Optional.of(this);
  }
}
