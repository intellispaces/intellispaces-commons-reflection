package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.session.Sessions;
import tech.intellispaces.framework.javastatements.statement.common.TypeElementFunctions;

import javax.lang.model.element.TypeElement;

public interface CustomTypes {

  static CustomStatement of(TypeElement typeElement) {
    return TypeElementFunctions.asCustomTypeStatement(typeElement, Sessions.get());
  }

  static CustomStatement of(TypeElement typeElement, Session session) {
    return TypeElementFunctions.asCustomTypeStatement(typeElement, session);
  }

  static CustomStatement of(Class<?> aClass) {
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
