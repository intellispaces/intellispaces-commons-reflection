package intellispaces.common.javastatements.method;

import intellispaces.common.javastatements.context.TypeContext;
import intellispaces.common.javastatements.customtype.CustomType;
import intellispaces.common.javastatements.customtype.CustomTypes;
import intellispaces.common.javastatements.session.Session;

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
