package intellispaces.common.javastatement.method;

import intellispaces.common.javastatement.instance.AnnotationInstance;
import intellispaces.common.javastatement.reference.TypeReference;

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
