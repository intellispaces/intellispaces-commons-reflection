package intellispaces.common.javastatements.customtype;

import intellispaces.common.javastatements.StatementType;
import intellispaces.common.javastatements.StatementTypes;
import intellispaces.common.javastatements.reference.CustomTypeReference;
import intellispaces.common.type.TypeFunctions;
import intellispaces.common.javastatements.method.MethodStatement;

import java.util.List;
import java.util.Optional;

/**
 * Adapter of {@link Class} to {@link ClassType}.
 */
class ClassBasedOnLangClass extends AbstractCustomTypeBasedLandClass implements ClassType {

  ClassBasedOnLangClass(Class<?> aClass) {
    super(aClass);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Class;
  }

  @Override
  public boolean isAbstract() {
    return TypeFunctions.isAbstractClass(aClass);
  }

  @Override
  public List<MethodStatement> constructors() {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public Optional<CustomTypeReference> extendedClass() {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public List<CustomTypeReference> implementedInterfaces() {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
