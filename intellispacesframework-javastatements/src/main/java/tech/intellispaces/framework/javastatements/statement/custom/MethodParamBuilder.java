package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.javastatements.statement.reference.TypeReference;
import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.instance.AnnotationInstance;

import javax.lang.model.element.VariableElement;
import java.util.List;
import java.util.Objects;

public final class MethodParamBuilder {
  private String name;
  private TypeReference type;
  private List<AnnotationInstance> annotations = List.of();

  public static MethodParamBuilder get() {
    return new MethodParamBuilder();
  }

  public static MethodParam build(VariableElement variableElement, TypeContext typeContext, Session session) {
    return new MethodParamAdapter(variableElement, typeContext, session);
  }

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

  private MethodParamBuilder() {}
}
