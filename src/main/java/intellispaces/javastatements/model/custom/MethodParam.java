package intellispaces.javastatements.model.custom;

import intellispaces.javastatements.model.AnnotatedStatement;
import intellispaces.javastatements.model.StatementType;
import intellispaces.javastatements.model.reference.TypeReference;
import intellispaces.javastatements.object.StatementTypes;

/**
 * Formal method parameter.
 */
public interface MethodParam extends AnnotatedStatement {

  String name();

  TypeReference type();

  @Override
  default StatementType statementType() {
    return StatementTypes.MethodParam;
  }
}
