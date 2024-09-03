package intellispaces.common.javastatements.customtype;

import intellispaces.common.javastatements.StatementType;
import intellispaces.common.javastatements.StatementTypes;

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
}
