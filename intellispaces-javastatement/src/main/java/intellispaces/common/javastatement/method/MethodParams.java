package intellispaces.common.javastatement.method;

import intellispaces.common.javastatement.context.TypeContext;
import intellispaces.common.javastatement.session.Session;

import javax.lang.model.element.VariableElement;

public interface MethodParams {

  static MethodParam of(VariableElement variableElement, TypeContext typeContext, Session session) {
    return new MethodParamBasedOnVariableElement(variableElement, typeContext, session);
  }

  static MethodParamBuilder build() {
    return new MethodParamBuilder();
  }
}
