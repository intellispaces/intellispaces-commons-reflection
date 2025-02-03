package tech.intellispaces.commons.java.reflection.method;

import tech.intellispaces.commons.base.exception.NotImplementedExceptions;
import tech.intellispaces.commons.java.reflection.StatementType;
import tech.intellispaces.commons.java.reflection.StatementTypes;
import tech.intellispaces.commons.java.reflection.customtype.AnnotationFunctions;
import tech.intellispaces.commons.java.reflection.instance.AnnotationInstance;
import tech.intellispaces.commons.java.reflection.instance.Instance;
import tech.intellispaces.commons.java.reflection.reference.NamedReference;
import tech.intellispaces.commons.java.reflection.reference.NotPrimitiveReference;
import tech.intellispaces.commons.java.reflection.reference.ThrowableReference;
import tech.intellispaces.commons.java.reflection.reference.TypeReference;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

class MethodSignatureImpl implements MethodSignature {
  private final String name;
  private final boolean isAbstract;
  private final boolean isPublic;
  private final boolean isDefault;
  private final boolean isStatic;
  private final List<NamedReference> typeParameters;
  private final TypeReference returnTypeReference;
  private final Instance defaultValue;
  private final List<MethodParam> params;
  private final List<ThrowableReference> exceptions;
  private final List<AnnotationInstance> annotations;
  private final Map<String, AnnotationInstance> annotationMap;

  MethodSignatureImpl(
      String name,
      boolean isAbstract,
      boolean isPublic,
      boolean isDefault,
      boolean isStatic,
      List<NamedReference> typeParameters,
      TypeReference returnTypeReference,
      Instance defaultValue,
      List<MethodParam> params,
      List<ThrowableReference> exceptions,
      List<AnnotationInstance> annotations
  ) {
    this.name = name;
    this.isAbstract = isAbstract;
    this.isPublic = isPublic;
    this.isDefault = isDefault;
    this.isStatic = isStatic;
    this.typeParameters = typeParameters;
    this.returnTypeReference = returnTypeReference;
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
  public boolean isAbstract() {
    return isAbstract;
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
  public List<NamedReference> typeParameters() {
    return typeParameters;
  }

  @Override
  public Optional<TypeReference> returnType() {
    return Optional.ofNullable(returnTypeReference);
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
  public List<TypeReference> parameterTypes() {
    return params().stream()
        .map(MethodParam::type)
        .toList();
  }

  @Override
  public List<ThrowableReference> exceptions() {
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

  @Override
  public MethodSignature effective(Map<String, NotPrimitiveReference> typeMapping) {
    return new MethodSignatureImpl(
        name(),
        isAbstract(),
        isPublic(),
        isDefault(),
        isStatic(),
        typeParameters(),
        returnType().map(t -> t.effective(typeMapping)).orElse(null),
        defaultValue().orElse(null),
        params().stream().map(p -> p.effective(typeMapping)).toList(),
        exceptions().stream().map(e -> (ThrowableReference) e.effective(typeMapping)).toList(),
        annotations()
    );
  }

  @Override
  public String prettyDeclaration() {
    throw NotImplementedExceptions.withCode("nzCKqg");
  }
}
