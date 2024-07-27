package tech.intellispaces.framework.javastatements.statement.method;

import tech.intellispaces.framework.commons.type.TypeFunctions;
import tech.intellispaces.framework.javastatements.JavaStatements;
import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.instance.AnnotationInstance;
import tech.intellispaces.framework.javastatements.statement.instance.Instance;
import tech.intellispaces.framework.javastatements.statement.instance.InstanceFunctions;
import tech.intellispaces.framework.javastatements.statement.type.ExceptionCompatibleType;
import tech.intellispaces.framework.javastatements.statement.type.NamedType;
import tech.intellispaces.framework.javastatements.statement.type.NonPrimitiveType;
import tech.intellispaces.framework.javastatements.statement.type.Type;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Adapter of {@link Method} to {@link MethodSignature}.
 */
class MethodSignatureBasedOnLangMethod implements MethodSignature {
  private final Method method;

  MethodSignatureBasedOnLangMethod(Method method) {
    this.method = method;
  }

  @Override
  public List<AnnotationInstance> annotations() {
    throw new RuntimeException("Not implemented yet");
  }

  @Override
  public Optional<AnnotationInstance> selectAnnotation(String annotationClass) {
    return Arrays.stream(method.getAnnotations())
        .filter(a -> annotationClass.equals(a.annotationType().getCanonicalName()))
        .map(InstanceFunctions::getAnnotationInstance)
        .findAny();
  }

  @Override
  public <A extends Annotation> Optional<A> selectAnnotation(Class<A> annotationClass) {
    return Optional.ofNullable(method.getAnnotation(annotationClass));
  }

  @Override
  public boolean hasAnnotation(Class<? extends Annotation> annotationClass) {
    return method.isAnnotationPresent(annotationClass);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.MethodSignature;
  }

  @Override
  public String name() {
    return method.getName();
  }

  @Override
  public List<NamedType> typeParameters() {
    throw new RuntimeException("Not implemented yet");
  }

  @Override
  public Optional<Type> returnType() {
    Class<?> returnClass = method.getReturnType();
    if ("void".equals(returnClass.getName())) {
      return Optional.empty();
    }
    return Optional.of(JavaStatements.customTypeReference(returnClass));
  }

  @Override
  public Optional<Instance> defaultValue() {
    throw new RuntimeException("Not implemented yet");
  }

  @Override
  public List<MethodParam> params() {
    return Arrays.stream(method.getParameters())
        .map(MethodFunctions::getMethodParam)
        .toList();
  }

  @Override
  public List<Type> parameterTypes() {
    throw new RuntimeException("Not implemented yet");
  }

  @Override
  public List<ExceptionCompatibleType> exceptions() {
    return Arrays.stream(method.getExceptionTypes())
        .map(JavaStatements::customTypeReference)
        .map(t -> (ExceptionCompatibleType) t)
        .toList();
  }

  @Override
  public boolean isAbstract() {
    return TypeFunctions.isAbstractMethod(method);
  }

  @Override
  public boolean isPublic() {
    throw new RuntimeException("Not implemented yet");
  }

  @Override
  public boolean isDefault() {
    throw new RuntimeException("Not implemented yet");
  }

  @Override
  public boolean isStatic() {
    throw new RuntimeException("Not implemented yet");
  }

  @Override
  public MethodSignature specify(Map<String, NonPrimitiveType> typeMapping) {
    return new MethodSignatureImpl(
        name(),
        isAbstract(),
        isPublic(),
        isDefault(),
        isStatic(),
        typeParameters(),
        returnType().map(t -> t.specify(typeMapping)).orElse(null),
        defaultValue().orElse(null),
        params().stream().map(p -> p.specify(typeMapping)).toList(),
        exceptions().stream().map(e -> (ExceptionCompatibleType) e.specify(typeMapping)).toList(),
        annotations()
    );
  }
}
