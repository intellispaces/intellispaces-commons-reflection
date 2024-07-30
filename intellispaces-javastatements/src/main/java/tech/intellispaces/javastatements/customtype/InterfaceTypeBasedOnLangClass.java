package tech.intellispaces.javastatements.customtype;

import tech.intellispaces.javastatements.StatementType;
import tech.intellispaces.javastatements.StatementTypes;

/**
 * Adapter of {@link Class} to {@link InterfaceType}.
 */
class InterfaceTypeBasedOnLangClass extends AbstractCustomTypeBasedLandClass implements InterfaceType {

  InterfaceTypeBasedOnLangClass(Class<?> aClass) {
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
