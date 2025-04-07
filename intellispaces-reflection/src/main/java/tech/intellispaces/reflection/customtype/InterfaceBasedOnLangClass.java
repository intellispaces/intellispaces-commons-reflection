package tech.intellispaces.reflection.customtype;

import tech.intellispaces.reflection.StatementType;
import tech.intellispaces.reflection.StatementTypes;

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
