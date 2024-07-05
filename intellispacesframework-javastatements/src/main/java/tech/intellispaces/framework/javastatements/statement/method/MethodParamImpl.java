package tech.intellispaces.framework.javastatements.statement.method;

import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.custom.AnnotationFunctions;
import tech.intellispaces.framework.javastatements.statement.instance.AnnotationInstance;
import tech.intellispaces.framework.javastatements.statement.reference.TypeReference;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

class MethodParamImpl implements MethodParam {
  private final String name;
  private final TypeReference type;
  private final List<AnnotationInstance> annotations;
  private final Map<String, AnnotationInstance> annotationMap;

  MethodParamImpl(String name, TypeReference type, List<AnnotationInstance> annotations) {
    this.name = name;
    this.type = type;
    this.annotations = annotations;
    this.annotationMap = annotations.stream().collect(Collectors.toMap(ant -> ant.annotationStatement().canonicalName(), Function.identity()));
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
    return type;
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
