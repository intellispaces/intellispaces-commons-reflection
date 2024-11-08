package intellispaces.common.javastatement.customtype;

import intellispaces.common.base.exception.NotImplementedExceptions;
import intellispaces.common.base.type.ClassFunctions;
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
    return ClassFunctions.isAbstractClass(aClass);
  }

  @Override
  public boolean isFinal() {
    return ClassFunctions.isFinalClass(aClass);
  }

  @Override
  public List<MethodStatement> constructors() {
    throw NotImplementedExceptions.withCode("r9BszQ");
  }

  @Override
  public Optional<CustomTypeReference> extendedClass() {
    throw NotImplementedExceptions.withCode("1n/QHw");
  }

  @Override
  public List<CustomTypeReference> implementedInterfaces() {
    throw NotImplementedExceptions.withCode("yHb2vQ");
  }
}
