package tech.intellispaces.javastatements.method;

import tech.intellispaces.javastatements.StatementType;
import tech.intellispaces.javastatements.StatementTypes;
import tech.intellispaces.javastatements.instance.AnnotationInstance;
import tech.intellispaces.javastatements.reference.NotPrimitiveTypeReference;
import tech.intellispaces.javastatements.reference.TypeReference;
import tech.intellispaces.javastatements.customtype.AnnotationFunctions;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

class MethodParamImpl implements MethodParam {
  private final String name;
  private final TypeReference typeReference;
  private final List<AnnotationInstance> annotations;
  private final Map<String, AnnotationInstance> annotationMap;

  MethodParamImpl(String name, TypeReference typeReference, List<AnnotationInstance> annotations) {
    this.name = name;
    this.typeReference = typeReference;
    this.annotations = annotations;
    this.annotationMap = annotations.stream()
        .collect(Collectors.toMap(ant -> ant.annotationStatement().canonicalName(), Function.identity()));
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.MethodParam;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public TypeReference type() {
    return typeReference;
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
  public MethodParam specify(Map<String, NotPrimitiveTypeReference> typeMapping) {
    return new MethodParamImpl(
        name(),
        type().specify(typeMapping),
        annotations()
    );
  }
}
