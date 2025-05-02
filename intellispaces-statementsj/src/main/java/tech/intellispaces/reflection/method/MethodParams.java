package tech.intellispaces.statementsj.method;

import javax.lang.model.element.VariableElement;

import tech.intellispaces.statementsj.context.TypeContext;
import tech.intellispaces.statementsj.session.Session;

public interface MethodParams {

  static MethodParam of(VariableElement variableElement, TypeContext typeContext, Session session) {
    return new MethodParamBasedOnVariableElement(variableElement, typeContext, session);
  }

  static MethodParamBuilder build() {
    return new MethodParamBuilder();
  }
}
