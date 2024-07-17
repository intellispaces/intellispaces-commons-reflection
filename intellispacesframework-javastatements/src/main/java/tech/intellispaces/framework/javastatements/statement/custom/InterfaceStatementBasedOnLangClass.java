package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;

/**
 * Adapter of {@link Class} to {@link InterfaceStatement}.
 */
class InterfaceStatementBasedOnLangClass extends AbstractCustomTypeBasedLandClass implements InterfaceStatement {

  InterfaceStatementBasedOnLangClass(Class<?> aClass) {
    super(aClass);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Interface;
  }

  @Override
  public boolean isAbstract() {
    return true;
  }
}
