package tech.intellispaces.jstatements.method;

import javax.lang.model.element.VariableElement;

import tech.intellispaces.jstatements.context.TypeContext;
import tech.intellispaces.jstatements.session.Session;

public interface MethodParams {

  static MethodParam of(VariableElement variableElement, TypeContext typeContext, Session session) {
    return new MethodParamBasedOnVariableElement(variableElement, typeContext, session);
  }

  static MethodParamBuilder build() {
    return new MethodParamBuilder();
  }
}
