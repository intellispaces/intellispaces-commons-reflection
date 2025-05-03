package tech.intellispaces.jstatements.customtype;

import java.util.List;
import javax.lang.model.element.TypeElement;

import tech.intellispaces.jstatements.StatementType;
import tech.intellispaces.jstatements.StatementTypes;
import tech.intellispaces.jstatements.reference.CustomTypeReference;

/**
 * Adapter of {@link TypeElement} to {@link EnumType}.
 */
class EnumBasedOnClass extends AbstractCustomTypeBasedLandClass implements EnumType {

  EnumBasedOnClass(Class<?> aClass) {
    super(aClass);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Enum;
  }

  @Override
  public boolean isAbstract() {
    return false;
  }

  @Override
  public boolean isFinal() {
    return true;
  }

  @Override
  public List<CustomTypeReference> implementedInterfaces() {
    return parentTypes();
  }
}
