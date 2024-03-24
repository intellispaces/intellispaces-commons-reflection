package intellispaces.javastatements.object.custom;

import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.custom.MethodParam;
import intellispaces.javastatements.model.custom.MethodSignature;
import intellispaces.javastatements.model.instance.AnnotationInstance;
import intellispaces.javastatements.model.instance.Instance;
import intellispaces.javastatements.model.reference.ExceptionCompatibleTypeReference;
import intellispaces.javastatements.model.reference.NamedTypeReference;
import intellispaces.javastatements.model.reference.TypeReference;
import intellispaces.javastatements.model.session.Session;

import javax.lang.model.element.ExecutableElement;
import java.util.List;
import java.util.Objects;

public final class MethodSignatureBuilder {
  private String name;
  private boolean isPublic = true;
  private boolean isDefault = false;
  private boolean isStatic = false;
  private List<NamedTypeReference> typeParameters = List.of();
  private TypeReference returnType = null;
  private Instance defaultValue = null;
  private List<MethodParam> params = List.of();
  private List<ExceptionCompatibleTypeReference> exceptions = List.of();
  private List<AnnotationInstance> annotations = List.of();

  public static MethodSignatureBuilder get() {
    return new MethodSignatureBuilder();
  }

  public static MethodSignature build(ExecutableElement executableElement, NameContext nameContext, Session session) {
    return new MethodSignatureAdapter(executableElement, nameContext, session);
  }

  public MethodSignatureBuilder name(String name) {
    this.name = name;
    return this;
  }

  public MethodSignatureBuilder isPublic(boolean isPublic) {
    this.isPublic = isPublic;
    return this;
  }

  public MethodSignatureBuilder isDefault(boolean isDefault) {
    this.isDefault = isDefault;
    return this;
  }

  public MethodSignatureBuilder isStatic(boolean isStatic) {
    this.isStatic = isStatic;
    return this;
  }

  public MethodSignatureBuilder typeParameters(List<NamedTypeReference> typeParameters) {
    this.typeParameters = typeParameters;
    return this;
  }

  public MethodSignatureBuilder returnType(TypeReference returnType) {
    this.returnType = returnType;
    return this;
  }

  public MethodSignatureBuilder defaultValue(Instance defaultValue) {
    this.defaultValue = defaultValue;
    return this;
  }

  public MethodSignatureBuilder params(List<MethodParam> params) {
    this.params = params;
    return this;
  }

  public MethodSignatureBuilder exceptions(List<ExceptionCompatibleTypeReference> exceptions) {
    this.exceptions = exceptions;
    return this;
  }

  public MethodSignatureBuilder annotations(List<AnnotationInstance> annotations) {
    this.annotations = annotations;
    return this;
  }

  public MethodSignature build() {
    validate();
    return new MethodSignatureObject(
      name,
      isPublic,
      isDefault,
      isStatic,
      typeParameters,
      returnType,
      defaultValue,
      params,
      exceptions,
      annotations
    );
  }

  private void validate() {
    Objects.requireNonNull(name);
  }

  private MethodSignatureBuilder() {}
}
