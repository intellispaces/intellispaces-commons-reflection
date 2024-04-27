package tech.intellispacesframework.javastatements.statement.reference;

import tech.intellispacesframework.javastatements.statement.Statement;
import tech.intellispacesframework.javastatements.statement.custom.CustomType;

import java.util.Collection;
import java.util.Optional;

/**
 * The type reference.
 */
public interface TypeReference extends Statement {

  boolean isPrimitive();

  /**
   * Related primitive type reference.
   */
  default Optional<PrimitiveTypeReference> asPrimitiveTypeReference() {
    return Optional.empty();
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

  /**
   * Related named type reference.
   */
  default Optional<NamedTypeReference> asNamedTypeReference() {
    return Optional.empty();
  }

  /**
   * Related wildcard type reference.
   */
  default Optional<WildcardTypeReference> asWildcardTypeReference() {
    return Optional.empty();
  }

  /**
   * Related custom type reference.
   */
  default Optional<CustomTypeReference> asCustomTypeReference() {
    return Optional.empty();
  }

  Collection<CustomType> dependencies();

  Collection<String> dependencyTypenames();

  /**
   * Type reference declaration.
   */
  String referenceDeclaration();

  /**
   * Full declaration of the related type.
   */
  String typeFullDeclaration();

  /**
   * Brief declaration of the related type.
   */
  String typeBriefDeclaration();
}
