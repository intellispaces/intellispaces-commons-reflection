package intellispaces.javastatements.statement.custom;

import intellispaces.javastatements.statement.AnnotatedStatement;
import intellispaces.javastatements.statement.instance.Instance;
import intellispaces.javastatements.statement.reference.ExceptionCompatibleTypeReference;
import intellispaces.javastatements.statement.reference.NamedTypeReference;
import intellispaces.javastatements.statement.reference.TypeReference;

import java.util.List;
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
  List<NamedTypeReference> typeParameters();

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

  /**
   * Method exceptions.
   */
  List<ExceptionCompatibleTypeReference> exceptions();

  boolean isPublic();

  /**
   * Default method sign.
   */
  boolean isDefault();

  /**
   * Static method sign.
   */
  boolean isStatic();
}
