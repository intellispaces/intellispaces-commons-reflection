package tech.intellispaces.reflection.method;

import tech.intellispaces.reflection.AnnotatedStatement;
import tech.intellispaces.reflection.customtype.CustomType;
import tech.intellispaces.reflection.instance.AnnotationInstance;
import tech.intellispaces.reflection.instance.Instance;
import tech.intellispaces.reflection.reference.NamedReference;
import tech.intellispaces.reflection.reference.NotPrimitiveReference;
import tech.intellispaces.reflection.reference.ThrowableReference;
import tech.intellispaces.reflection.reference.TypeReference;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MethodStatement extends AnnotatedStatement {

  /**
   * Method owner.
   */
  CustomType owner();

  /**
   * Method signature.
   */
  MethodSignature signature();

  /**
   * List of override methods.
   */
  List<MethodStatement> overrideMethods();

  MethodStatement effective(Map<String, NotPrimitiveReference> typeMapping);

  /**
   * Declared type parameters.
   */
  default List<NamedReference> typeParameters() {
    return signature().typeParameters();
  }

  /**
   * Method name.
   */
  default String name() {
    return signature().name();
  }

  /**
   * Method returned type.
   */
  default Optional<TypeReference> returnType() {
    return signature().returnType();
  }

  /**
   * Method formal parameters.
   */
  default List<MethodParam> params() {
    return signature().params();
  }

  default List<TypeReference> parameterTypes() {
    return signature().parameterTypes();
  }

  /**
   * Method exceptions.
   */
  default List<ThrowableReference> exceptions() {
    return signature().exceptions();
  }

  /**
   * Assignment annotations.
   */
  default List<AnnotationInstance> annotations() {
    return signature().annotations();
  }

  @Override
  default Optional<AnnotationInstance> selectAnnotation(String annotationClass) {
    return signature().selectAnnotation(annotationClass);
  }

  @Override
  default <A extends Annotation> Optional<A> selectAnnotation(Class<A> annotationClass) {
    return signature().selectAnnotation(annotationClass);
  }

  @Override
  default boolean hasAnnotation(Class<? extends Annotation> annotationClass) {
    return signature().hasAnnotation(annotationClass);
  }

  /**
   * Method default value.
   */
  default Optional<Instance> defaultValue() {
    return signature().defaultValue();
  }

  default boolean isAbstract() {
    return signature().isAbstract();
  }

  /**
   * Default method sign.
   */
  default boolean isDefault() {
    return signature().isDefault();
  }

  default boolean isPublic() {
    return signature().isPublic();
  }

  /**
   * Static method sign.
   */
  default boolean isStatic() {
    return signature().isStatic();
  }
}
