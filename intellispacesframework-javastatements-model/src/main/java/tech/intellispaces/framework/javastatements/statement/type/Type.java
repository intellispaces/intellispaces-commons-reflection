package tech.intellispaces.framework.javastatements.statement.type;

import tech.intellispaces.framework.javastatements.statement.Statement;
import tech.intellispaces.framework.javastatements.statement.custom.CustomStatement;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * The type.
 */
public interface Type extends Statement {

  boolean isPrimitive();

  boolean isArrayType();

  boolean isCustomType();

  boolean isNamedType();

  boolean isWildcardType();

  /**
   * Related primitive type.
   */
  default Optional<PrimitiveType> asPrimitiveType() {
    return Optional.empty();
  }

  default PrimitiveType asPrimitiveTypeConfidently() {
    return asPrimitiveType().orElseThrow();
  }

  /**
   * Related non-primitive type.
   */
  default Optional<NonPrimitiveType> asNonPrimitiveType() {
    return Optional.empty();
  }

  /**
   * Related array type.
   */
  default Optional<ArrayType> asArrayType() {
    return Optional.empty();
  }

  default ArrayType asArrayTypeConfidently() {
    return asArrayType().orElseThrow();
  }

  /**
   * Related named type.
   */
  default Optional<NamedType> asNamedType() {
    return Optional.empty();
  }

  default NamedType asNamedTypeConfidently() {
    return asNamedType().orElseThrow();
  }

  /**
   * Related wildcard type.
   */
  default Optional<WildcardType> asWildcardType() {
    return Optional.empty();
  }

  default WildcardType asWildcardTypeConfidently() {
    return asWildcardType().orElseThrow();
  }

  /**
   * Related custom type.
   */
  default Optional<CustomType> asCustomType() {
    return Optional.empty();
  }

  default CustomType asCustomTypeConfidently() {
    return asCustomType().orElseThrow();
  }

  Type specify(Map<String, NonPrimitiveType> typeMapping);

  Collection<CustomStatement> dependencies();

  Collection<String> dependencyTypenames();

  /**
   * Actual type declaration.
   */
  String actualDeclaration();

  /**
   * Actual type declaration.
   */
  String actualDeclaration(Function<String, String> simpleNameMapper);

  String actualBlindDeclaration(Function<String, String> simpleNameMapper);

  /**
   * Formal full type declaration.
   */
  String formalFullDeclaration();

  /**
   * Formal brief type declaration.
   */
  String formalBriefDeclaration();
}
