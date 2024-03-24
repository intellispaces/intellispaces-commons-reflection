package intellispaces.javastatements.object.custom;

import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.custom.MethodParam;
import intellispaces.javastatements.model.instance.AnnotationInstance;
import intellispaces.javastatements.model.reference.TypeReference;
import intellispaces.javastatements.model.session.Session;

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

  public static MethodParam build(VariableElement variableElement, NameContext nameContext, Session session) {
    return new MethodParamAdapter(variableElement, nameContext, session);
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
    return new MethodParamObject(name, type, annotations);
  }

  private void validate() {
    Objects.requireNonNull(name);
    Objects.requireNonNull(type);
  }

  private MethodParamBuilder() {}
}
