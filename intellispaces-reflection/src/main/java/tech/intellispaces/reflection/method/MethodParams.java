package tech.intellispaces.reflection.method;

import tech.intellispaces.reflection.context.TypeContext;
import tech.intellispaces.reflection.session.Session;

import javax.lang.model.element.VariableElement;

public interface MethodParams {

  static MethodParam of(VariableElement variableElement, TypeContext typeContext, Session session) {
    return new MethodParamBasedOnVariableElement(variableElement, typeContext, session);
  }

  static MethodParamBuilder build() {
    return new MethodParamBuilder();
  }
}
