package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.context.TypeContexts;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.TypeElementFunctions;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

public interface RecordStatements {

  static RecordStatement of(TypeElement typeElement, Session session) {
    return of(typeElement, TypeContexts.empty(), session);
  }

  static RecordStatement of(
      TypeElement typeElement, TypeContext typeContext, Session session
  ) {
    return TypeElementFunctions.asCustomTypeStatement(
        typeElement,
        ElementKind.RECORD,
        RecordStatements::build,
        typeContext,
        session
    );
  }

  private static RecordStatement build(TypeElement typeElement, TypeContext typeContext, Session session) {
    return new RecordStatementBasedOnTypeElement(typeElement, typeContext, session);
  }
}
