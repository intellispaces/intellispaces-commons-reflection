package tech.intellispaces.javareflection.method;

import tech.intellispaces.commons.exception.NotImplementedExceptions;
import tech.intellispaces.javareflection.JavaStatements;
import tech.intellispaces.javareflection.StatementType;
import tech.intellispaces.javareflection.StatementTypes;
import tech.intellispaces.javareflection.instance.AnnotationInstance;
import tech.intellispaces.javareflection.instance.AnnotationInstances;
import tech.intellispaces.javareflection.instance.Instance;
import tech.intellispaces.javareflection.instance.InstanceFunctions;
import tech.intellispaces.javareflection.reference.NamedReference;
import tech.intellispaces.javareflection.reference.NamedReferences;
import tech.intellispaces.javareflection.reference.NotPrimitiveReference;
import tech.intellispaces.javareflection.reference.ReferenceBound;
import tech.intellispaces.javareflection.reference.ThrowableReference;
import tech.intellispaces.javareflection.reference.TypeReference;
import tech.intellispaces.javareflection.reference.TypeReferences;
import tech.intellispaces.javareflection.session.Sessions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    List<NamedReference> typeParams = new ArrayList<>();
    for (TypeVariable<Method> typeVariable : method.getTypeParameters()) {

      typeParams.add(NamedReferences.get(
          typeVariable.getName(),
          this,
          Arrays.stream(typeVariable.getBounds())
              .map(b -> (ReferenceBound) TypeReferences.of(b))
              .toList()
      ));
    }
    return Collections.unmodifiableList(typeParams);
  }

  @Override
  public Optional<TypeReference> returnType() {
    Class<?> returnClass = method.getReturnType();
    if ("void".equals(returnClass.getName())) {
      return Optional.empty();
    }
    return Optional.of(TypeReferences.of(returnClass));
  }

  @Override
  public Optional<Instance> defaultValue() {
    if (!isDefault() || method.getDefaultValue() == null) {
      return Optional.empty();
    }
    return Optional.ofNullable(InstanceFunctions.objectToInstance(method.getDefaultValue(), Sessions.get()));
  }

  @Override
  public List<MethodParam> params() {
    return Arrays.stream(method.getParameters())
        .map(MethodFunctions::getMethodParam)
        .toList();
  }

  @Override
  public List<TypeReference> parameterTypes() {
    return Arrays.stream(method.getParameters())
        .map(MethodFunctions::getMethodParam)
        .map(MethodParam::type)
        .toList();
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
    return tech.intellispaces.commons.type.MethodFunctions.isAbstractMethod(method);
  }

  @Override
  public boolean isPublic() {
    return tech.intellispaces.commons.type.MethodFunctions.isPublicMethod(method);
  }

  @Override
  public boolean isDefault() {
    return method.isDefault();
  }

  @Override
  public boolean isStatic() {
    return tech.intellispaces.commons.type.MethodFunctions.isStaticMethod(method);
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
    throw NotImplementedExceptions.withCode("Xx3tNg");
  }
}
