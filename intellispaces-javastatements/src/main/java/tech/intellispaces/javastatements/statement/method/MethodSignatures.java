package tech.intellispaces.javastatements.statement.method;

import tech.intellispaces.javastatements.context.TypeContext;
import tech.intellispaces.javastatements.session.Session;

import javax.lang.model.element.ExecutableElement;
import java.lang.reflect.Method;

public interface MethodSignatures {

  static MethodSignature of(Method method) {
    return MethodFunctions.getMethodSignature(method);
  }

  static MethodSignature of(
      ExecutableElement executableElement, TypeContext typeContext, Session session
  ) {
    return MethodFunctions.getMethodSignature(executableElement, typeContext, session);
  }

  static MethodSignatureBuilder build() {
    return new MethodSignatureBuilder();
  }
}
