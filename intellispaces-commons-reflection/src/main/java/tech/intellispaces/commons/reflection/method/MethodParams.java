package tech.intellispaces.commons.reflection.method;

import tech.intellispaces.commons.reflection.context.TypeContext;
import tech.intellispaces.commons.reflection.session.Session;

import javax.lang.model.element.VariableElement;

public interface MethodParams {

  static MethodParam of(VariableElement variableElement, TypeContext typeContext, Session session) {
    return new MethodParamBasedOnVariableElement(variableElement, typeContext, session);
  }

  static MethodParamBuilder build() {
    return new MethodParamBuilder();
  }
}
