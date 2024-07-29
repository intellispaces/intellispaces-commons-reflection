package tech.intellispaces.javastatements.statement.method;

import tech.intellispaces.javastatements.context.TypeContext;
import tech.intellispaces.javastatements.session.Session;

import javax.lang.model.element.VariableElement;

public interface MethodParams {

  static MethodParam of(VariableElement variableElement, TypeContext typeContext, Session session) {
    return new MethodParamBasedOnVariableElement(variableElement, typeContext, session);
  }

  static MethodParamBuilder build() {
    return new MethodParamBuilder();
  }
}
