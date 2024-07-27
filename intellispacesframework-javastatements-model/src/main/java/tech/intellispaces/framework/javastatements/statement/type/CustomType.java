package tech.intellispaces.framework.javastatements.statement.type;

import tech.intellispaces.framework.javastatements.statement.custom.CustomStatement;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * The custom type.
 */
public interface CustomType extends NotPrimitiveType, TypeBound, ExceptionCompatibleType {

  /**
   * Custom statement.
   */
  CustomStatement statement();

  /**
   * Actual arguments of type parameters.
   */
  List<NotPrimitiveType> typeArguments();

  CustomStatement effectiveStatement();

  Map<String, NotPrimitiveType> typeArgumentMapping();

  String typeArgumentsDeclaration();

  String typeArgumentsDeclaration(Function<String, String> simpleNameMapper);

  @Override
  default boolean isArray() {
    return false;
  }

  @Override
  default boolean isCustom() {
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
  default Optional<CustomType> asCustom() {
    return Optional.of(this);
  }
}
