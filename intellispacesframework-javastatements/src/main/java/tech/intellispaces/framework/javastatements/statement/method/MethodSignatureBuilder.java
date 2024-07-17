package tech.intellispaces.framework.javastatements.statement.method;

import tech.intellispaces.framework.javastatements.statement.instance.AnnotationInstance;
import tech.intellispaces.framework.javastatements.statement.instance.Instance;
import tech.intellispaces.framework.javastatements.statement.reference.ExceptionCompatibleTypeReference;
import tech.intellispaces.framework.javastatements.statement.reference.NamedTypeReference;
import tech.intellispaces.framework.javastatements.statement.reference.TypeReference;

import java.util.List;
import java.util.Objects;

public final class MethodSignatureBuilder {
  private String name;
  private boolean isAbstract = false;
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

  public MethodSignatureBuilder name(String name) {
    this.name = name;
    return this;
  }

  public MethodSignatureBuilder isAbstract(boolean isAbstract) {
    this.isAbstract = isAbstract;
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
    return new MethodSignatureImpl(
      name,
      isAbstract,
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
