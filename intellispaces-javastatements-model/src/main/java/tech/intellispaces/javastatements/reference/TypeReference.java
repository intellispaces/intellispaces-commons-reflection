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

  boolean isPrimitiveReference();

  boolean isArrayReference();

  boolean isCustomTypeReference();

  boolean isNamedReference();

  boolean isWildcard();

  /**
   * Related primitive type.
   */
  default Optional<PrimitiveReference> asPrimitiveReference() {
    return Optional.empty();
  }

  default PrimitiveReference asPrimitiveReferenceConfidently() {
    return asPrimitiveReference().orElseThrow();
  }

  /**
   * Related non-primitive type.
   */
  default Optional<NotPrimitiveReference> asNotPrimitiveReference() {
    return Optional.empty();
  }

  /**
   * Related array type.
   */
  default Optional<ArrayReference> asArrayReference() {
    return Optional.empty();
  }

  default ArrayReference asArrayReferenceConfidently() {
    return asArrayReference().orElseThrow();
  }

  /**
   * Related named type.
   */
  default Optional<NamedReference> asNamedReference() {
    return Optional.empty();
  }

  default NamedReference asNamedReferenceConfidently() {
    return asNamedReference().orElseThrow();
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
  default Optional<CustomTypeReference> asCustomTypeReference() {
    return Optional.empty();
  }

  default CustomTypeReference asCustomTypeReferenceConfidently() {
    return asCustomTypeReference().orElseThrow();
  }

  TypeReference specify(Map<String, NotPrimitiveReference> typeMapping);

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
