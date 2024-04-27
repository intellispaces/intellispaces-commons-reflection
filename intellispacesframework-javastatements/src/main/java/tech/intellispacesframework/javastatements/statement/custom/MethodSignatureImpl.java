package tech.intellispacesframework.javastatements.statement.custom;

import tech.intellispacesframework.javastatements.statement.StatementType;
import tech.intellispacesframework.javastatements.statement.StatementTypes;
import tech.intellispacesframework.javastatements.statement.instance.AnnotationInstance;
import tech.intellispacesframework.javastatements.statement.instance.Instance;
import tech.intellispacesframework.javastatements.statement.reference.ExceptionCompatibleTypeReference;
import tech.intellispacesframework.javastatements.statement.reference.NamedTypeReference;
import tech.intellispacesframework.javastatements.statement.reference.TypeReference;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

class MethodSignatureImpl implements MethodSignature {
  private final String name;
  private final boolean isPublic;
  private final boolean isDefault;
  private final boolean isStatic;
  private final List<NamedTypeReference> typeParameters;
  private final TypeReference returnType;
  private final Instance defaultValue;
  private final List<MethodParam> params;
  private final List<ExceptionCompatibleTypeReference> exceptions;
  private final List<AnnotationInstance> annotations;
  private final Map<String, AnnotationInstance> annotationMap;

  MethodSignatureImpl(
      String name,
      boolean isPublic,
      boolean isDefault,
      boolean isStatic,
      List<NamedTypeReference> typeParameters,
      TypeReference returnType,
      Instance defaultValue,
      List<MethodParam> params,
      List<ExceptionCompatibleTypeReference> exceptions,
      List<AnnotationInstance> annotations
  ) {
    this.name = name;
    this.isPublic = isPublic;
    this.isDefault = isDefault;
    this.isStatic = isStatic;
    this.typeParameters = typeParameters;
    this.returnType = returnType;
    this.defaultValue = defaultValue;
    this.params = params;
    this.exceptions = exceptions;
    this.annotations = annotations;
    this.annotationMap = annotations.stream().collect(Collectors.toMap(ant -> ant.annotationStatement().canonicalName(), Function.identity()));
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.MethodSignature;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public boolean isPublic() {
    return isPublic;
  }

  @Override
  public boolean isDefault() {
    return isDefault;
  }

  @Override
  public boolean isStatic() {
    return isStatic;
  }

  @Override
  public List<NamedTypeReference> typeParameters() {
    return typeParameters;
  }

  @Override
  public Optional<TypeReference> returnType() {
    return Optional.ofNullable(returnType);
  }

  @Override
  public Optional<Instance> defaultValue() {
    return Optional.ofNullable(defaultValue);
  }

  @Override
  public List<MethodParam> params() {
    return params;
  }

  @Override
  public List<ExceptionCompatibleTypeReference> exceptions() {
    return exceptions;
  }

  @Override
  public List<AnnotationInstance> annotations() {
    return annotations;
  }

  @Override
  public Optional<AnnotationInstance> selectAnnotation(String annotationClass) {
    return Optional.ofNullable(annotationMap.get(annotationClass));
  }

  @Override
  public <A extends Annotation> Optional<A> selectAnnotation(Class<A> annotationClass) {
    return Optional.ofNullable(annotationMap.get(annotationClass.getCanonicalName()))
        .map(ant -> AnnotationFunctions.asAnnotation(ant, annotationClass));
  }

  @Override
  public boolean hasAnnotation(Class<? extends Annotation> annotationClass) {
    return annotationMap.containsKey(annotationClass.getCanonicalName());
  }
}
