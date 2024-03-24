package intellispaces.javastatements.model.reference;

import intellispaces.javastatements.model.StatementType;
import intellispaces.javastatements.object.StatementTypes;

import java.util.List;
import java.util.Optional;

/**
 * Named type reference
 *
 * <p>By default, named type reference is referred to java.lang.Object.
 */
public interface NamedTypeReference extends NonPrimitiveTypeReference, TypeBoundReference, ExceptionCompatibleTypeReference {

  String name();

  List<TypeBoundReference> extendedBounds();

  @Override
  default StatementType statementType() {
    return StatementTypes.NamedTypeReference;
  }

  @Override
  default Optional<NamedTypeReference> asNamedTypeReference() {
    return Optional.of(this);
  }
}
