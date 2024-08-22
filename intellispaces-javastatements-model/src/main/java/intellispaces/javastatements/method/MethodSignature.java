package intellispaces.javastatements.method;

import intellispaces.javastatements.AnnotatedStatement;
import intellispaces.javastatements.instance.Instance;
import intellispaces.javastatements.reference.NamedReference;
import intellispaces.javastatements.reference.NotPrimitiveReference;
import intellispaces.javastatements.reference.ThrowableReference;
import intellispaces.javastatements.reference.TypeReference;

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

  MethodSignature specify(Map<String, NotPrimitiveReference> typeMapping);
}
