package tech.intellispaces.java.reflection.reference;

import tech.intellispaces.java.reflection.Statement;
import tech.intellispaces.java.reflection.customtype.CustomType;

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

  boolean isVoidType();

  /**
   * Related primitive type.
   */
  default Optional<PrimitiveReference> asPrimitiveReference() {
    return Optional.empty();
  }

  default PrimitiveReference asPrimitiveReferenceOrElseThrow() {
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

  default ArrayReference asArrayReferenceOrElseThrow() {
    return asArrayReference().orElseThrow();
  }

  /**
   * Related named type.
   */
  default Optional<NamedReference> asNamedReference() {
    return Optional.empty();
  }

  default NamedReference asNamedReferenceOrElseThrow() {
    return asNamedReference().orElseThrow();
  }

  /**
   * Related wildcard type.
   */
  default Optional<WildcardReference> asWildcard() {
    return Optional.empty();
  }

  default WildcardReference asWildcardOrElseThrow() {
    return asWildcard().orElseThrow();
  }

  /**
   * Related custom type.
   */
  default Optional<CustomTypeReference> asCustomTypeReference() {
    return Optional.empty();
  }

  default CustomTypeReference asCustomTypeReferenceOrElseThrow() {
    return asCustomTypeReference().orElseThrow();
  }

  TypeReference effective(Map<String, NotPrimitiveReference> typeMapping);

  Collection<CustomType> dependencies();

  Collection<String> dependencyTypenames();

  String simpleDeclaration();

  String simpleDeclaration(Function<String, String> nameMapper);

  /**
   * Actual type declaration.
   */
  String actualDeclaration();

  /**
   * Actual type declaration.
   */
  String actualDeclaration(Function<String, String> nameMapper);

  String actualBlindDeclaration(Function<String, String> nameMapper);

  /**
   * Formal full type declaration.
   */
  String formalFullDeclaration();

  /**
   * Formal brief type declaration.
   */
  String formalBriefDeclaration();
}
