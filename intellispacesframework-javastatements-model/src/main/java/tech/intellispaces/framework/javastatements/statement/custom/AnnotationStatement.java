package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.javastatements.statement.reference.NamedTypeReference;

import java.util.List;
import java.util.Optional;

/**
 * The annotation statement.
 */
public interface AnnotationStatement extends CustomType {

  @Override
  default List<NamedTypeReference> typeParameters() {
    return List.of();
  }

  @Override
  default Optional<AnnotationStatement> asAnnotation() {
    return Optional.of(this);
  }

  default CustomType asCustomType() {
    return this;
  }
}
