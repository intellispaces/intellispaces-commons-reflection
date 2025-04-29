package tech.intellispaces.reflection.customtype;

import java.util.List;
import java.util.Optional;

import tech.intellispaces.commons.exception.NotImplementedExceptions;
import tech.intellispaces.commons.type.ClassFunctions;
import tech.intellispaces.reflection.StatementType;
import tech.intellispaces.reflection.StatementTypes;
import tech.intellispaces.reflection.method.MethodStatement;
import tech.intellispaces.reflection.reference.CustomTypeReference;

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
