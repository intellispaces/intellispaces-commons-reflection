package tech.intellispaces.commons.java.reflection.method;

import tech.intellispaces.commons.java.reflection.instance.AnnotationInstance;
import tech.intellispaces.commons.java.reflection.instance.Instance;
import tech.intellispaces.commons.java.reflection.reference.NamedReference;
import tech.intellispaces.commons.java.reflection.reference.ThrowableReference;
import tech.intellispaces.commons.java.reflection.reference.TypeReference;

import java.util.List;
import java.util.Objects;

public final class MethodSignatureBuilder {
  private String name;
  private boolean isAbstract = false;
  private boolean isPublic = true;
  private boolean isDefault = false;
  private boolean isStatic = false;
  private List<NamedReference> typeParameters = List.of();
  private TypeReference returnTypeReference = null;
  private Instance defaultValue = null;
  private List<MethodParam> params = List.of();
  private List<ThrowableReference> exceptions = List.of();
  private List<AnnotationInstance> annotations = List.of();

  MethodSignatureBuilder() {}

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

  public MethodSignatureBuilder typeParameters(List<NamedReference> typeParameters) {
    this.typeParameters = typeParameters;
    return this;
  }

  public MethodSignatureBuilder returnType(TypeReference returnTypeReference) {
    this.returnTypeReference = returnTypeReference;
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

  public MethodSignatureBuilder exceptions(List<ThrowableReference> exceptions) {
    this.exceptions = exceptions;
    return this;
  }

  public MethodSignatureBuilder annotations(List<AnnotationInstance> annotations) {
    this.annotations = annotations;
    return this;
  }

  public MethodSignature get() {
    validate();
    return new MethodSignatureImpl(
      name,
      isAbstract,
      isPublic,
      isDefault,
      isStatic,
      typeParameters,
      returnTypeReference,
      defaultValue,
      params,
      exceptions,
      annotations
    );
  }

  private void validate() {
    Objects.requireNonNull(name);
  }
}
