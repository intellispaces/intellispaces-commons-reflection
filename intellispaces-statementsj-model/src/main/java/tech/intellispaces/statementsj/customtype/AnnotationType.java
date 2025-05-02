package tech.intellispaces.statementsj.customtype;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import tech.intellispaces.statementsj.reference.NamedReference;

/**
 * The annotation type.
 */
public interface AnnotationType extends CustomType {

  @Override
  default List<NamedReference> typeParameters() {
    return List.of();
  }

  @Override
  default Map<String, NamedReference> typeParameterMap() {
    return Map.of();
  }

  @Override
  default Optional<AnnotationType> asAnnotation() {
    return Optional.of(this);
  }

  default CustomType asCustomType() {
    return this;
  }
}
