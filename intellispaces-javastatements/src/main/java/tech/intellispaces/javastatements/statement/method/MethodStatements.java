package tech.intellispaces.javastatements.statement.method;

import tech.intellispaces.javastatements.context.TypeContext;
import tech.intellispaces.javastatements.session.Session;
import tech.intellispaces.javastatements.statement.customtype.CustomType;

import javax.lang.model.element.ExecutableElement;

public interface MethodStatements {

  static MethodStatement of(
      ExecutableElement executableElement, CustomType owner, TypeContext typeContext, Session session
  ) {
    return new MethodStatementBasedOnExecutableElement(executableElement, owner, typeContext, session);
  }

  static MethodStatement build(CustomType owner, MethodSignature signature) {
    return new MethodStatementImpl(owner, signature);
  }
}
