package intellispaces.javastatements.model.custom;

import intellispaces.javastatements.model.AnnotatedStatement;
import intellispaces.javastatements.model.StatementType;
import intellispaces.javastatements.model.instance.AnnotationInstance;
import intellispaces.javastatements.model.instance.Instance;
import intellispaces.javastatements.model.reference.ExceptionCompatibleTypeReference;
import intellispaces.javastatements.model.reference.NamedTypeReference;
import intellispaces.javastatements.model.reference.TypeReference;
import intellispaces.javastatements.object.StatementTypes;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

public interface MethodStatement extends AnnotatedStatement {

  @Override
  default StatementType statementType() {
    return StatementTypes.Method;
  }

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
