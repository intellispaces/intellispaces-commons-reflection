package tech.intellispaces.reflection.customtype;

import java.util.List;
import javax.lang.model.element.TypeElement;

import tech.intellispaces.reflection.StatementType;
import tech.intellispaces.reflection.StatementTypes;
import tech.intellispaces.reflection.reference.CustomTypeReference;

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
