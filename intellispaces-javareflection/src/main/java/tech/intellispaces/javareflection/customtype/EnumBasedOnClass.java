package tech.intellispaces.javareflection.customtype;

import tech.intellispaces.javareflection.StatementType;
import tech.intellispaces.javareflection.StatementTypes;
import tech.intellispaces.javareflection.reference.CustomTypeReference;

import javax.lang.model.element.TypeElement;
import java.util.List;

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
