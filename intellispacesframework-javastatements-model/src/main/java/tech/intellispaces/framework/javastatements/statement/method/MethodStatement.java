package tech.intellispaces.framework.javastatements.statement.method;

import tech.intellispaces.framework.javastatements.statement.AnnotatedStatement;
import tech.intellispaces.framework.javastatements.statement.custom.CustomStatement;
import tech.intellispaces.framework.javastatements.statement.instance.AnnotationInstance;
import tech.intellispaces.framework.javastatements.statement.instance.Instance;
import tech.intellispaces.framework.javastatements.statement.type.ExceptionCompatibleType;
import tech.intellispaces.framework.javastatements.statement.type.NamedType;
import tech.intellispaces.framework.javastatements.statement.type.NotPrimitiveType;
import tech.intellispaces.framework.javastatements.statement.type.Type;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MethodStatement extends AnnotatedStatement {

  /**
   * Method owner.
   */
  CustomStatement owner();

  /**
   * Method signature.
   */
  MethodSignature signature();

  /**
   * List of override methods.
   */
  List<MethodStatement> overrideMethods();

  MethodStatement specify(Map<String, NotPrimitiveType> typeMapping);

  /**
   * Declared type parameters.
   */
  default List<NamedType> typeParameters() {
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
  default Optional<Type> returnType() {
    return signature().returnType();
  }

  /**
   * Method formal parameters.
   */
  default List<MethodParam> params() {
    return signature().params();
  }

  default List<Type> parameterTypes() {
    return signature().parameterTypes();
  }

  /**
   * Method exceptions.
   */
  default List<ExceptionCompatibleType> exceptions() {
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
