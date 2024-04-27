package tech.intellispacesframework.javastatements.statement.custom;

import tech.intellispacesframework.javastatements.context.TypeContext;
import tech.intellispacesframework.javastatements.statement.instance.AnnotationInstance;
import tech.intellispacesframework.javastatements.statement.reference.TypeReference;
import tech.intellispacesframework.javastatements.session.Session;

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
