package tech.intellispacesframework.javastatements.statement.custom;

import tech.intellispacesframework.javastatements.context.TypeContext;
import tech.intellispacesframework.javastatements.session.Session;

import javax.lang.model.element.ExecutableElement;

public interface MethodStatementBuilder {

  static MethodStatement build(CustomType holder, MethodSignature signature) {
    return new MethodStatementImpl(holder, signature);
  }

  static MethodStatement build(ExecutableElement executableElement, CustomType holder, TypeContext typeContext, Session session) {
    return new MethodStatementAdapter(executableElement, holder, typeContext, session);
  }
}
