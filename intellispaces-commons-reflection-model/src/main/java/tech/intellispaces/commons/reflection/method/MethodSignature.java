package tech.intellispaces.commons.reflection.method;

import tech.intellispaces.commons.reflection.AnnotatedStatement;
import tech.intellispaces.commons.reflection.instance.Instance;
import tech.intellispaces.commons.reflection.reference.NamedReference;
import tech.intellispaces.commons.reflection.reference.NotPrimitiveReference;
import tech.intellispaces.commons.reflection.reference.ThrowableReference;
import tech.intellispaces.commons.reflection.reference.TypeReference;

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
  List<ThrowableReference> exceptions();

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

  MethodSignature effective(Map<String, NotPrimitiveReference> typeMapping);
}
