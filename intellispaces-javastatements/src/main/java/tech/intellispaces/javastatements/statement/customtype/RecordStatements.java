package tech.intellispaces.javastatements.statement.customtype;

import tech.intellispaces.javastatements.context.TypeContext;
import tech.intellispaces.javastatements.context.TypeContexts;
import tech.intellispaces.javastatements.session.Session;
import tech.intellispaces.javastatements.statement.common.TypeElementFunctions;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

public interface RecordStatements {

  static RecordType of(TypeElement typeElement, Session session) {
    return of(typeElement, TypeContexts.empty(), session);
  }

  static RecordType of(
      TypeElement typeElement, TypeContext typeContext, Session session
  ) {
    return TypeElementFunctions.asCustomStatement(
        typeElement,
        ElementKind.RECORD,
        RecordStatements::create,
        typeContext,
        session
    );
  }

  private static RecordType create(
      TypeElement typeElement, TypeContext typeContext, Session session
  ) {
    return new RecordStatementBasedOnTypeElement(typeElement, typeContext, session);
  }
}
