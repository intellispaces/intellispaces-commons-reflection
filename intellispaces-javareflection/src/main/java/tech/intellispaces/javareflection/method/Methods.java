package tech.intellispaces.javareflection.method;

import java.lang.reflect.Method;
import java.util.List;
import javax.lang.model.element.ExecutableElement;

import tech.intellispaces.commons.exception.UnexpectedExceptions;
import tech.intellispaces.javareflection.context.TypeContext;
import tech.intellispaces.javareflection.customtype.CustomType;
import tech.intellispaces.javareflection.customtype.CustomTypes;
import tech.intellispaces.javareflection.session.Session;

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
