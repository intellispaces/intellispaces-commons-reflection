package tech.intellispaces.reflection.method;

import java.lang.reflect.Method;
import javax.lang.model.element.ExecutableElement;

import tech.intellispaces.reflection.context.TypeContext;
import tech.intellispaces.reflection.session.Session;

public interface MethodSignatures {

  static MethodSignature get(Method method) {
    return new MethodSignatureBasedOnLangMethod(method);
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
