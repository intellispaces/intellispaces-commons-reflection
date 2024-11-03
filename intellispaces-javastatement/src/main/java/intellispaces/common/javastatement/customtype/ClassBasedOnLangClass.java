package intellispaces.common.javastatement.customtype;

import intellispaces.common.base.exception.NotImplementedException;
import intellispaces.common.base.type.TypeFunctions;
import intellispaces.common.javastatement.StatementType;
import intellispaces.common.javastatement.StatementTypes;
import intellispaces.common.javastatement.method.MethodStatement;
import intellispaces.common.javastatement.reference.CustomTypeReference;

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
  public boolean isFinal() {
    return TypeFunctions.isFinalClass(aClass);
  }

  @Override
  public List<MethodStatement> constructors() {
    throw NotImplementedException.withCode("r9BszQ");
  }

  @Override
  public Optional<CustomTypeReference> extendedClass() {
    throw NotImplementedException.withCode("1n/QHw");
  }

  @Override
  public List<CustomTypeReference> implementedInterfaces() {
    throw NotImplementedException.withCode("yHb2vQ");
  }
}
