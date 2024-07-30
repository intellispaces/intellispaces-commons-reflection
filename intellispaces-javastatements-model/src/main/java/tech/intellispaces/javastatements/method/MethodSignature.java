package tech.intellispaces.javastatements.method;

import tech.intellispaces.javastatements.AnnotatedStatement;
import tech.intellispaces.javastatements.instance.Instance;
import tech.intellispaces.javastatements.reference.NamedReference;
import tech.intellispaces.javastatements.reference.NotPrimitiveTypeReference;
import tech.intellispaces.javastatements.reference.ThrowableTypeReference;
import tech.intellispaces.javastatements.reference.TypeReference;

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
