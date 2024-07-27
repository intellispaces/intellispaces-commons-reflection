package tech.intellispaces.framework.javastatements.statement.type;

import tech.intellispaces.framework.javastatements.statement.custom.CustomStatement;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * The custom type.
 */
public interface CustomType extends NonPrimitiveType, TypeBound, ExceptionCompatibleType {

  /**
   * Target type.
   */
  CustomStatement targetType();

  /**
   * Actual arguments of type parameters.
   */
  List<NonPrimitiveType> typeArguments();

  CustomStatement effectiveTargetType();

  Map<String, NonPrimitiveType> typeArgumentMapping();

  String typeArgumentsDeclaration();

  String typeArgumentsDeclaration(Function<String, String> simpleNameMapper);

  @Override
  default boolean isArrayType() {
    return false;
  }

  @Override
  default boolean isCustomType() {
    return true;
  }

  @Override
  default boolean isNamedType() {
    return false;
  }

  @Override
  default boolean isWildcardType() {
    return false;
  }

  @Override
  default Optional<CustomType> asCustomType() {
    return Optional.of(this);
  }
}
