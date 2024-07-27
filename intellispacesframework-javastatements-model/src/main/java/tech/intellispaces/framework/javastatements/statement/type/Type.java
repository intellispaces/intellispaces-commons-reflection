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

  boolean isArray();

  boolean isCustom();

  boolean isNamed();

  boolean isWildcard();

  /**
   * Related primitive type.
   */
  default Optional<PrimitiveType> asPrimitive() {
    return Optional.empty();
  }

  default PrimitiveType asPrimitiveConfidently() {
    return asPrimitive().orElseThrow();
  }

  /**
   * Related non-primitive type.
   */
  default Optional<NotPrimitiveType> asNotPrimitive() {
    return Optional.empty();
  }

  /**
   * Related array type.
   */
  default Optional<ArrayType> asArray() {
    return Optional.empty();
  }

  default ArrayType asArrayConfidently() {
    return asArray().orElseThrow();
  }

  /**
   * Related named type.
   */
  default Optional<NamedType> asNamed() {
    return Optional.empty();
  }

  default NamedType asNamedConfidently() {
    return asNamed().orElseThrow();
  }

  /**
   * Related wildcard type.
   */
  default Optional<WildcardType> asWildcard() {
    return Optional.empty();
  }

  default WildcardType asWildcardConfidently() {
    return asWildcard().orElseThrow();
  }

  /**
   * Related custom type.
   */
  default Optional<CustomType> asCustom() {
    return Optional.empty();
  }

  default CustomType asCustomConfidently() {
    return asCustom().orElseThrow();
  }

  Type specify(Map<String, NotPrimitiveType> typeMapping);

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
