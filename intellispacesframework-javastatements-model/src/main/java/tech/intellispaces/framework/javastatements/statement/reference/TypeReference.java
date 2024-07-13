package tech.intellispaces.framework.javastatements.statement.reference;

import tech.intellispaces.framework.javastatements.statement.Statement;
import tech.intellispaces.framework.javastatements.statement.custom.CustomType;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * The type reference.
 */
public interface TypeReference extends Statement {

  boolean isPrimitive();

  boolean isArrayTypeReference();

  boolean isCustomTypeReference();

  boolean isNamedTypeReference();

  boolean isWildcardTypeReference();

  /**
   * Related primitive type reference.
   */
  default Optional<PrimitiveTypeReference> asPrimitiveTypeReference() {
    return Optional.empty();
  }

  default PrimitiveTypeReference asPrimitiveTypeReferenceSurely() {
    return asPrimitiveTypeReference().orElseThrow();
  }

  /**
   * Related non-primitive type reference.
   */
  default Optional<NonPrimitiveTypeReference> asNonPrimitiveTypeReference() {
    return Optional.empty();
  }

  /**
   * Related array type reference.
   */
  default Optional<ArrayTypeReference> asArrayTypeReference() {
    return Optional.empty();
  }

  default ArrayTypeReference asArrayTypeReferenceSurely() {
    return asArrayTypeReference().orElseThrow();
  }

  /**
   * Related named type reference.
   */
  default Optional<NamedTypeReference> asNamedTypeReference() {
    return Optional.empty();
  }

  default NamedTypeReference asNamedTypeReferenceSurely() {
    return asNamedTypeReference().orElseThrow();
  }

  /**
   * Related wildcard type reference.
   */
  default Optional<WildcardTypeReference> asWildcardTypeReference() {
    return Optional.empty();
  }

  default WildcardTypeReference asWildcardTypeReferenceSurely() {
    return asWildcardTypeReference().orElseThrow();
  }

  /**
   * Related custom type reference.
   */
  default Optional<CustomTypeReference> asCustomTypeReference() {
    return Optional.empty();
  }

  default CustomTypeReference asCustomTypeReferenceSurely() {
    return asCustomTypeReference().orElseThrow();
  }

  TypeReference specify(Map<String, NonPrimitiveTypeReference> typeMapping);

  Collection<CustomType> dependencies();

  Collection<String> dependencyTypenames();

  /**
   * Actual type reference declaration.
   */
  String actualDeclaration();

  /**
   * Actual type reference declaration.
   */
  String actualDeclaration(Function<String, String> simpleNameMapper);

  String actualBlindDeclaration(Function<String, String> simpleNameMapper);

  /**
   * Formal full reference declaration.
   */
  String formalFullDeclaration();

  /**
   * Formal brief reference declaration.
   */
  String formalBriefDeclaration();
}
