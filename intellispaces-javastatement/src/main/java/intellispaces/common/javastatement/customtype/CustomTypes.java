package intellispaces.common.javastatement.customtype;

import intellispaces.common.javastatement.common.JavaModelFunctions;
import intellispaces.common.javastatement.session.Session;
import intellispaces.common.javastatement.session.Sessions;

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
      throw new UnsupportedOperationException("Not implemented yet (Fee42g)");
    } else if (aClass.isEnum()) {
      throw new UnsupportedOperationException("Not implemented (YTWBWA)");
    } else if (aClass.isAnnotation()) {
      throw new UnsupportedOperationException("Not implemented yet (TZCZ4g)");
    } else {
      return Classes.of(aClass);
    }
  }
}
