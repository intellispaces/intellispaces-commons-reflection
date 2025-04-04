package tech.intellispaces.commons.reflection.method;

import tech.intellispaces.commons.exception.UnexpectedExceptions;
import tech.intellispaces.commons.reflection.context.TypeContext;
import tech.intellispaces.commons.reflection.customtype.CustomType;
import tech.intellispaces.commons.reflection.customtype.CustomTypes;
import tech.intellispaces.commons.reflection.session.Session;

import javax.lang.model.element.ExecutableElement;
import java.lang.reflect.Method;
import java.util.List;

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

  static MethodStatement of(Class<?> aClass, String name, Class<?>... paramClasses) {
    try {
      return of(aClass.getMethod(name, paramClasses));
    } catch (NoSuchMethodException e) {
      throw UnexpectedExceptions.withCauseAndMessage(e,
          "Could not find method {0} in class {1}", name, aClass.getCanonicalName());
    }
  }

  static MethodStatement of(Class<?> aClass, String name, List<Class<?>> paramClasses) {
    return of(aClass, name, paramClasses.toArray(new Class<?>[0]));
  }
}
