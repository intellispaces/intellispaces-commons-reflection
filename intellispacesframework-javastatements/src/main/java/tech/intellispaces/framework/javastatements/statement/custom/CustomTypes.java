package tech.intellispaces.framework.javastatements.statement.custom;

public interface CustomTypes {

  static CustomType of(Class<?> aClass) {
    if (aClass.isInterface()) {
      return InterfaceStatements.of(aClass);
    } else if (aClass.isRecord()) {
      throw new UnsupportedOperationException("Not implemented");
    } else if (aClass.isEnum()) {
      throw new UnsupportedOperationException("Not implemented");
    } else if (aClass.isAnnotation()) {
      throw new UnsupportedOperationException("Not implemented");
    } else {
      return ClassStatements.of(aClass);
    }
  }
}
