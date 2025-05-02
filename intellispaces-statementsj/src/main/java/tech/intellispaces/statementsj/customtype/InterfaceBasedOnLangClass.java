package tech.intellispaces.statementsj.customtype;

import tech.intellispaces.statementsj.StatementType;
import tech.intellispaces.statementsj.StatementTypes;

/**
 * Adapter of {@link Class} to {@link InterfaceType}.
 */
class InterfaceBasedOnLangClass extends AbstractCustomTypeBasedLandClass implements InterfaceType {

  InterfaceBasedOnLangClass(Class<?> aClass) {
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

  @Override
  public boolean isFinal() {
    return false;
  }
}
