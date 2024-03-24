package intellispaces.javastatements.model.custom;

import intellispaces.javastatements.model.Statement;
import intellispaces.javastatements.model.StatementType;
import intellispaces.javastatements.model.reference.TypeReference;
import intellispaces.javastatements.object.StatementTypes;

public interface ClassField extends Statement {

  @Override
  default StatementType statementType() {
    return StatementTypes.ClassField;
  }

  String name();

  TypeReference type();

  boolean isFinal();
}
