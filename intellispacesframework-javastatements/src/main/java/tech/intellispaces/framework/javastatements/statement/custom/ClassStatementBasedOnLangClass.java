package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.commons.type.TypeFunctions;
import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.method.MethodStatement;
import tech.intellispaces.framework.javastatements.statement.type.CustomType;

import java.util.List;
import java.util.Optional;

/**
 * Adapter of {@link Class} to {@link ClassStatement}.
 */
class ClassStatementBasedOnLangClass extends AbstractCustomStatementBasedLandClass implements ClassStatement {

  ClassStatementBasedOnLangClass(Class<?> aClass) {
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
  public Optional<CustomType> extendedClass() {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public List<CustomType> implementedInterfaces() {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
