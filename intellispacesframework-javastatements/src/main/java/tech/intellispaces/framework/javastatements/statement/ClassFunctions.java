package tech.intellispaces.framework.javastatements.statement;

import tech.intellispaces.framework.javastatements.statement.custom.ClassStatementBuilder;
import tech.intellispaces.framework.javastatements.statement.custom.CustomType;
import tech.intellispaces.framework.javastatements.statement.reference.CustomTypeReference;
import tech.intellispaces.framework.javastatements.statement.reference.CustomTypeReferenceBuilder;

public interface ClassFunctions {

  static CustomType asCustomTypeStatement(Class<?> aClass) {
    if (aClass.isInterface()) {
      throw new UnsupportedOperationException("Not implemented");
    } else if (aClass.isRecord()) {
      throw new UnsupportedOperationException("Not implemented");
    } else if (aClass.isEnum()) {
      throw new UnsupportedOperationException("Not implemented");
    } else if (aClass.isAnnotation()) {
      throw new UnsupportedOperationException("Not implemented");
    } else {
      return ClassStatementBuilder.build(aClass);
    }
  }

  static CustomTypeReference asCustomTypeReference(Class<?> aClass) {
    return CustomTypeReferenceBuilder.build(aClass);
  }
}
