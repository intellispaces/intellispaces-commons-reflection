package tech.intellispaces.javastatements.statement.method;

import tech.intellispaces.javastatements.statement.AnnotatedStatement;
import tech.intellispaces.javastatements.statement.instance.Instance;
import tech.intellispaces.javastatements.statement.reference.NamedReference;
import tech.intellispaces.javastatements.statement.reference.NotPrimitiveTypeReference;
import tech.intellispaces.javastatements.statement.reference.ThrowableTypeReference;
import tech.intellispaces.javastatements.statement.reference.TypeReference;

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
  List<NamedReference> typeParameters();

  /**
   * Method returned type.
   */
  Optional<TypeReference> returnType();

  /**
   * Method default value.
   */
  Optional<Instance> defaultValue();

  /**
   * Method formal parameters.
   */
  List<MethodParam> params();

  List<TypeReference> parameterTypes();

  /**
   * Method exceptions.
   */
  List<ThrowableTypeReference> exceptions();

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

  MethodSignature specify(Map<String, NotPrimitiveTypeReference> typeMapping);
}
