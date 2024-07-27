package tech.intellispaces.framework.javastatements.statement.method;

import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.custom.CustomStatement;

import javax.lang.model.element.ExecutableElement;

public interface MethodStatements {

  static MethodStatement of(
      ExecutableElement executableElement, CustomStatement owner, TypeContext typeContext, Session session
  ) {
    return new MethodStatementBasedOnExecutableElement(executableElement, owner, typeContext, session);
  }

  static MethodStatement build(CustomStatement owner, MethodSignature signature) {
    return new MethodStatementImpl(owner, signature);
  }
}
