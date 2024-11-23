package tech.intellispaces.java.reflection.instance;

import tech.intellispaces.java.reflection.Statement;

import java.util.Optional;

/**
 * Specific object instance.
 */
public interface Instance extends Statement {

  default Optional<PrimitiveInstance> asPrimitive() {
    return Optional.empty();
  }

  default Optional<StringInstance> asString() {
    return Optional.empty();
  }

  default Optional<ClassInstance> asClass() {
    return Optional.empty();
  }

  default Optional<EnumInstance> asEnum() {
    return Optional.empty();
  }

  default Optional<ArrayInstance> asArray() {
    return Optional.empty();
  }

  default Optional<AnnotationInstance> asAnnotation() {
    return Optional.empty();
  }
}
