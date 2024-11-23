package tech.intellispaces.java.reflection.customtype;

import tech.intellispaces.java.reflection.StatementType;
import tech.intellispaces.java.reflection.StatementTypes;
import tech.intellispaces.java.reflection.method.MethodStatement;
import tech.intellispaces.java.reflection.reference.CustomTypeReference;
import tech.intellispaces.entity.exception.NotImplementedExceptions;
import tech.intellispaces.entity.type.ClassFunctions;

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
