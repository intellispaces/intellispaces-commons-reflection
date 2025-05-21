package tech.intellispaces.javareflection.method;

import tech.intellispaces.javareflection.instance.AnnotationInstance;
import tech.intellispaces.javareflection.reference.TypeReference;

import java.util.List;
import java.util.Objects;

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
