package tech.intellispaces.java.reflection.method;

import tech.intellispaces.java.reflection.StatementType;
import tech.intellispaces.java.reflection.StatementTypes;
import tech.intellispaces.java.reflection.customtype.AnnotationFunctions;
import tech.intellispaces.java.reflection.instance.AnnotationInstance;
import tech.intellispaces.java.reflection.reference.NotPrimitiveReference;
import tech.intellispaces.java.reflection.reference.TypeReference;
import tech.intellispaces.entity.exception.NotImplementedExceptions;

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
  public MethodParam effective(Map<String, NotPrimitiveReference> typeMapping) {
    return new MethodParamImpl(
        name(),
        type().effective(typeMapping),
        annotations()
    );
  }

  @Override
  public String prettyDeclaration() {
    throw NotImplementedExceptions.withCode("AN08nQ");
  }
}
