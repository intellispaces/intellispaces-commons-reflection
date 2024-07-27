package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.javastatements.statement.type.NamedType;

import java.util.List;
import java.util.Optional;

/**
 * The annotation statement.
 */
public interface AnnotationStatement extends CustomStatement {

  @Override
  default List<NamedType> typeParameters() {
    return List.of();
  }

  @Override
  default Optional<AnnotationStatement> asAnnotation() {
    return Optional.of(this);
  }

  default CustomStatement asCustomType() {
    return this;
  }
}
