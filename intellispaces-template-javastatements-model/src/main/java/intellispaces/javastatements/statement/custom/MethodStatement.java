package intellispaces.javastatements.statement.custom;

import intellispaces.javastatements.statement.AnnotatedStatement;
import intellispaces.javastatements.statement.instance.AnnotationInstance;
import intellispaces.javastatements.statement.instance.Instance;
import intellispaces.javastatements.statement.reference.ExceptionCompatibleTypeReference;
import intellispaces.javastatements.statement.reference.NamedTypeReference;
import intellispaces.javastatements.statement.reference.TypeReference;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

public interface MethodStatement extends AnnotatedStatement {

  /**
   * Method holder.
   */
  CustomType holder();

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

  /**
   * Method signature.
   */
  MethodSignature signature();
}
