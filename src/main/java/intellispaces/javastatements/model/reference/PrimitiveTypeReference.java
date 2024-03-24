package intellispaces.javastatements.model.reference;

import intellispaces.javastatements.model.StatementType;
import intellispaces.javastatements.model.custom.CustomType;
import intellispaces.javastatements.object.StatementTypes;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * The reference to primitive type.
 */
public interface PrimitiveTypeReference extends TypeReference {

  String typename();

  Class<?> wrapperClass();

  @Override
  default StatementType statementType() {
    return StatementTypes.PrimitiveReference;
  }

  @Override
  default boolean isPrimitive() {
    return true;
  }

  @Override
  default Optional<PrimitiveTypeReference> asPrimitiveTypeReference() {
    return Optional.of(this);
  }

  @Override
  default Collection<CustomType> dependencies() {
    return List.of();
  }

  @Override
  default Collection<String> dependencyTypenames() {
    return List.of();
  }
}
