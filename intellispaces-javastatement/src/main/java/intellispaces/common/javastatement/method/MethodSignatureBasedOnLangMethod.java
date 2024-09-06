package intellispaces.common.javastatement.method;

import intellispaces.common.javastatement.JavaStatements;
import intellispaces.common.javastatement.StatementType;
import intellispaces.common.javastatement.StatementTypes;
import intellispaces.common.javastatement.reference.NotPrimitiveReference;
import intellispaces.common.javastatement.reference.ThrowableReference;
import intellispaces.common.base.type.TypeFunctions;
import intellispaces.common.javastatement.instance.AnnotationInstance;
import intellispaces.common.javastatement.instance.AnnotationInstances;
import intellispaces.common.javastatement.instance.Instance;
import intellispaces.common.javastatement.reference.NamedReference;
import intellispaces.common.javastatement.reference.TypeReference;

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
    return Arrays.stream(method.getAnnotations())
        .map(AnnotationInstances::of)
        .toList();
  }

  @Override
  public Optional<AnnotationInstance> selectAnnotation(String annotationClass) {
    return Arrays.stream(method.getAnnotations())
        .filter(a -> annotationClass.equals(a.annotationType().getCanonicalName()))
        .map(AnnotationInstances::of)
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
  public List<NamedReference> typeParameters() {
    throw new RuntimeException("Not implemented yet");
  }

  @Override
  public Optional<TypeReference> returnType() {
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
  public List<TypeReference> parameterTypes() {
    throw new RuntimeException("Not implemented yet");
  }

  @Override
  public List<ThrowableReference> exceptions() {
    return Arrays.stream(method.getExceptionTypes())
        .map(JavaStatements::customTypeReference)
        .map(t -> (ThrowableReference) t)
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
  public MethodSignature specify(Map<String, NotPrimitiveReference> typeMapping) {
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
        exceptions().stream().map(e -> (ThrowableReference) e.specify(typeMapping)).toList(),
        annotations()
    );
  }
}
