package tech.intellispaces.java.reflection.method;

import tech.intellispaces.java.reflection.context.TypeContext;
import tech.intellispaces.java.reflection.customtype.CustomType;
import tech.intellispaces.java.reflection.customtype.CustomTypes;
import tech.intellispaces.java.reflection.session.Session;

import javax.lang.model.element.ExecutableElement;
import java.lang.reflect.Method;

public interface Methods {

  static MethodStatement get(CustomType owner, MethodSignature signature) {
    return new MethodStatementImpl(owner, signature);
  }

  static MethodStatement of(Method method) {
    CustomType owner = CustomTypes.of(method.getDeclaringClass());
    MethodSignature signature = MethodSignatures.get(method);
    return new MethodStatementImpl(owner, signature);
  }

  static MethodStatement of(
      ExecutableElement executableElement, CustomType owner, TypeContext typeContext, Session session
  ) {
    return new MethodStatementBasedOnExecutableElement(executableElement, owner, typeContext, session);
  }

}
