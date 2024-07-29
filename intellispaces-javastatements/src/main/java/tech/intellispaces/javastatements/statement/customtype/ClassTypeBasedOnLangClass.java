package tech.intellispaces.javastatements.statement.customtype;

import tech.intellispaces.commons.type.TypeFunctions;
import tech.intellispaces.javastatements.statement.StatementType;
import tech.intellispaces.javastatements.statement.StatementTypes;
import tech.intellispaces.javastatements.statement.method.MethodStatement;
import tech.intellispaces.javastatements.statement.reference.CustomTypeReference;

import java.util.List;
import java.util.Optional;

/**
 * Adapter of {@link Class} to {@link ClassType}.
 */
class ClassTypeBasedOnLangClass extends AbstractCustomTypeBasedLandClass implements ClassType {

  ClassTypeBasedOnLangClass(Class<?> aClass) {
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
