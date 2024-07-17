package tech.intellispaces.framework.javastatements.statement.method;

import tech.intellispaces.framework.javastatements.statement.instance.AnnotationInstance;
import tech.intellispaces.framework.javastatements.statement.reference.TypeReference;

import java.util.List;
import java.util.Objects;

public final class MethodParamBuilder {
  private String name;
  private TypeReference type;
  private List<AnnotationInstance> annotations = List.of();

  public MethodParamBuilder name(String name) {
    this.name = name;
    return this;
  }

  public MethodParamBuilder type(TypeReference type) {
    this.type = type;
    return this;
  }

  public MethodParamBuilder annotations(List<AnnotationInstance> annotations) {
    this.annotations = annotations;
    return this;
  }

  public MethodParam build() {
    validate();
    return new MethodParamImpl(name, type, annotations);
  }

  private void validate() {
    Objects.requireNonNull(name);
    Objects.requireNonNull(type);
  }

  MethodParamBuilder() {}
}
