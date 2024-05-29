package tech.intellispacesframework.javastatements.statement;

import tech.intellispacesframework.javastatements.statement.custom.ClassStatementBuilder;
import tech.intellispacesframework.javastatements.statement.custom.CustomType;

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
}
