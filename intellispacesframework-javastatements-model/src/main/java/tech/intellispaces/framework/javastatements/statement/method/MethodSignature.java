package tech.intellispaces.framework.javastatements.statement.method;

import tech.intellispaces.framework.javastatements.statement.AnnotatedStatement;
import tech.intellispaces.framework.javastatements.statement.instance.Instance;
import tech.intellispaces.framework.javastatements.statement.type.ExceptionCompatibleType;
import tech.intellispaces.framework.javastatements.statement.type.NamedType;
import tech.intellispaces.framework.javastatements.statement.type.NotPrimitiveType;
import tech.intellispaces.framework.javastatements.statement.type.Type;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The method signature.
 */
public interface MethodSignature extends AnnotatedStatement {

  /**
   * Method name.
   */
  String name();

  /**
   * Declared type parameters.
   */
  List<NamedType> typeParameters();

  /**
   * Method returned type.
   */
  Optional<Type> returnType();

  /**
   * Method default value.
   */
  Optional<Instance> defaultValue();

  /**
   * Method formal parameters.
   */
  List<MethodParam> params();

  List<Type> parameterTypes();

  /**
   * Method exceptions.
   */
  List<ExceptionCompatibleType> exceptions();

  boolean isAbstract();

  boolean isPublic();

  /**
   * Default method sign.
   */
  boolean isDefault();

  /**
   * Static method sign.
   */
  boolean isStatic();

  MethodSignature specify(Map<String, NotPrimitiveType> typeMapping);
}
