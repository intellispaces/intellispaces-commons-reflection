package tech.intellispaces.javastatements.method;

import tech.intellispaces.javastatements.context.TypeContext;
import tech.intellispaces.javastatements.customtype.CustomType;
import tech.intellispaces.javastatements.customtype.CustomTypes;
import tech.intellispaces.javastatements.session.Session;

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
