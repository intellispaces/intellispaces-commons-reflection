package intellispaces.javastatements.model.custom;

import intellispaces.javastatements.model.AnnotatedStatement;
import intellispaces.javastatements.model.StatementType;
import intellispaces.javastatements.model.instance.Instance;
import intellispaces.javastatements.model.reference.ExceptionCompatibleTypeReference;
import intellispaces.javastatements.model.reference.NamedTypeReference;
import intellispaces.javastatements.model.reference.TypeReference;
import intellispaces.javastatements.object.StatementTypes;

import java.util.List;
import java.util.Optional;

/**
 * The method signature.
 */
public interface MethodSignature extends AnnotatedStatement {

  @Override
  default StatementType statementType() {
    return StatementTypes.MethodSignature;
  }

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
