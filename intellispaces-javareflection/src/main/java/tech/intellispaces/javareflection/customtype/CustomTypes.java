package tech.intellispaces.javareflection.customtype;

import tech.intellispaces.commons.exception.NotImplementedExceptions;
import tech.intellispaces.javareflection.common.JavaModelFunctions;
import tech.intellispaces.javareflection.session.Session;
import tech.intellispaces.javareflection.session.Sessions;

import javax.lang.model.element.TypeElement;

public interface CustomTypes {

  static CustomType of(TypeElement typeElement) {
    return JavaModelFunctions.asCustomStatement(typeElement, Sessions.get());
  }

  static CustomType of(TypeElement typeElement, Session session) {
    return JavaModelFunctions.asCustomStatement(typeElement, session);
  }

  static CustomType of(Class<?> aClass) {
    if (aClass.isInterface()) {
      return Interfaces.of(aClass);
    } else if (aClass.isRecord()) {
      throw NotImplementedExceptions.withCode("Fee42g");
    } else if (aClass.isEnum()) {
      throw NotImplementedExceptions.withCode("YTWBWA");
    } else if (aClass.isAnnotation()) {
      throw NotImplementedExceptions.withCode("TZCZ4g");
    } else {
      return Classes.of(aClass);
    }
  }
}
