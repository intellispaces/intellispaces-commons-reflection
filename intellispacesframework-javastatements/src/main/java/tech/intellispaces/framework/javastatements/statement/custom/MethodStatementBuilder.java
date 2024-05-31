package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.session.Session;

import javax.lang.model.element.ExecutableElement;

public interface MethodStatementBuilder {

  static MethodStatement build(CustomType holder, MethodSignature signature) {
    return new MethodStatementImpl(holder, signature);
  }

  static MethodStatement build(ExecutableElement executableElement, CustomType holder, TypeContext typeContext, Session session) {
    return new MethodStatementAdapter(executableElement, holder, typeContext, session);
  }
}
