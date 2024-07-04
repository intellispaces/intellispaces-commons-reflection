package tech.intellispaces.framework.javastatements.statement.reference;

import tech.intellispaces.framework.javastatements.statement.ClassFunctions;
import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.custom.CustomType;

import java.util.List;
import java.util.function.Function;

class ClassToCustomTypeReferenceAdapter extends AbstractTypeReference implements CustomTypeReference {
  private final Class<?> aClass;
  private CustomType targetType;

  public ClassToCustomTypeReferenceAdapter(Class<?> aClass) {
    this.aClass = aClass;
  }

  @Override
  public StatementType statementType() {
    if (aClass.isInterface()) {
      return StatementTypes.Interface;
    } else if (aClass.isRecord()) {
      return StatementTypes.Record;
    } else if (aClass.isEnum()) {
      return StatementTypes.Enum;
    } else if (aClass.isAnnotation()) {
      return StatementTypes.Annotation;
    } else {
      return StatementTypes.Class;
    }
  }

  @Override
  public CustomType targetType() {
    if (targetType == null) {
      targetType = ClassFunctions.asCustomTypeStatement(aClass);
    }
    return targetType;
  }

  @Override
  public List<NonPrimitiveTypeReference> typeArguments() {
    return List.of();
  }

  @Override
  public String typeArgumentsDeclaration() {
    return "";
  }

  @Override
  public String typeArgumentsDeclaration(Function<String, String> simpleNameMapper) {
    return "";
  }
}
