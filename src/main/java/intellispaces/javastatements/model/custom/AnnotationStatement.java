package intellispaces.javastatements.model.custom;

import intellispaces.javastatements.model.StatementType;
import intellispaces.javastatements.model.reference.NamedTypeReference;
import intellispaces.javastatements.object.StatementTypes;

import java.util.List;
import java.util.Optional;

/**
 * The annotation statement.
 */
public interface AnnotationStatement extends CustomType {

  @Override
  default StatementType statementType() {
    return StatementTypes.Annotation;
  }

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
