package tech.intellispaces.reflection.method;

import java.util.List;
import java.util.Objects;

import tech.intellispaces.reflection.instance.AnnotationInstance;
import tech.intellispaces.reflection.reference.TypeReference;

public final class MethodParamBuilder {
  private String name;
  private TypeReference typeReference;
  private List<AnnotationInstance> annotations = List.of();

  MethodParamBuilder() {}

  public MethodParamBuilder name(String name) {
    this.name = name;
    return this;
  }

  public MethodParamBuilder type(TypeReference typeReference) {
    this.typeReference = typeReference;
    return this;
  }

  public MethodParamBuilder annotations(List<AnnotationInstance> annotations) {
    this.annotations = annotations;
    return this;
  }

  public MethodParam get() {
    validate();
    return new MethodParamImpl(name, typeReference, annotations);
  }

  private void validate() {
    Objects.requireNonNull(name);
    Objects.requireNonNull(typeReference);
  }
}
