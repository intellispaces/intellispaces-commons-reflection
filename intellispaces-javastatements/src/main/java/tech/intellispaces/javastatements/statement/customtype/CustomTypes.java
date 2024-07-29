package tech.intellispaces.javastatements.statement.customtype;

import tech.intellispaces.javastatements.session.Session;
import tech.intellispaces.javastatements.session.Sessions;
import tech.intellispaces.javastatements.statement.common.TypeElementFunctions;

import javax.lang.model.element.TypeElement;

public interface CustomTypes {

  static CustomType of(TypeElement typeElement) {
    return TypeElementFunctions.asCustomStatement(typeElement, Sessions.get());
  }

  static CustomType of(TypeElement typeElement, Session session) {
    return TypeElementFunctions.asCustomStatement(typeElement, session);
  }

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
