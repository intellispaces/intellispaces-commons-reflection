package tech.intellispaces.framework.javastatements.statement.method;

import tech.intellispaces.framework.javastatements.statement.AnnotatedStatement;
import tech.intellispaces.framework.javastatements.statement.custom.CustomType;
import tech.intellispaces.framework.javastatements.statement.instance.AnnotationInstance;
import tech.intellispaces.framework.javastatements.statement.instance.Instance;
import tech.intellispaces.framework.javastatements.statement.reference.ExceptionCompatibleTypeReference;
import tech.intellispaces.framework.javastatements.statement.reference.NamedTypeReference;
import tech.intellispaces.framework.javastatements.statement.reference.NonPrimitiveTypeReference;
import tech.intellispaces.framework.javastatements.statement.reference.TypeReference;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MethodStatement extends AnnotatedStatement {

  /**
   * Method holder.
   */
  CustomType holder();

  /**
   * Method signature.
   */
  MethodSignature signature();

  /**
   * List of override methods.
   */
  List<MethodStatement> overrideMethods();

  MethodStatement specify(Map<String, NonPrimitiveTypeReference> typeMapping);

  /**
   * Declared type parameters.
   */
  default List<NamedTypeReference> typeParameters() {
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

  /**
   * Method exceptions.
   */
  default List<ExceptionCompatibleTypeReference> exceptions() {
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
