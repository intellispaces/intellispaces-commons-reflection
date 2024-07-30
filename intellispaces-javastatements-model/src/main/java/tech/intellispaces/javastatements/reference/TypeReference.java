package tech.intellispaces.javastatements.reference;

import tech.intellispaces.javastatements.Statement;
import tech.intellispaces.javastatements.customtype.CustomType;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * The type reference.
 */
public interface TypeReference extends Statement {

  boolean isPrimitive();

  boolean isArray();

  boolean isCustomType();

  boolean isNamed();

  boolean isWildcard();

  /**
   * Related primitive type.
   */
  default Optional<PrimitiveReference> asPrimitive() {
    return Optional.empty();
  }

  default PrimitiveReference asPrimitiveConfidently() {
    return asPrimitive().orElseThrow();
  }

  /**
   * Related non-primitive type.
   */
  default Optional<NotPrimitiveTypeReference> asNotPrimitive() {
    return Optional.empty();
  }

  /**
   * Related array type.
   */
  default Optional<ArrayReference> asArray() {
    return Optional.empty();
  }

  default ArrayReference asArrayConfidently() {
    return asArray().orElseThrow();
  }

  /**
   * Related named type.
   */
  default Optional<NamedReference> asNamed() {
    return Optional.empty();
  }

  default NamedReference asNamedConfidently() {
    return asNamed().orElseThrow();
  }

  /**
   * Related wildcard type.
   */
  default Optional<WildcardReference> asWildcard() {
    return Optional.empty();
  }

  default WildcardReference asWildcardConfidently() {
    return asWildcard().orElseThrow();
  }

  /**
   * Related custom type.
   */
  default Optional<CustomTypeReference> asCustomType() {
    return Optional.empty();
  }

  default CustomTypeReference asCustomTypeConfidently() {
    return asCustomType().orElseThrow();
  }

  TypeReference specify(Map<String, NotPrimitiveTypeReference> typeMapping);

  Collection<CustomType> dependencies();

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
