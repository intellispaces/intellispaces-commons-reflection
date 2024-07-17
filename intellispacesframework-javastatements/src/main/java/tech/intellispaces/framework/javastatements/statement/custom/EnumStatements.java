package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.context.TypeContexts;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.TypeElementFunctions;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

public interface EnumStatements {

  static EnumStatement of(TypeElement typeElement, Session session) {
    return of(typeElement, TypeContexts.empty(), session);
  }

  static EnumStatement of(TypeElement typeElement, TypeContext typeContext, Session session) {
    return TypeElementFunctions.asCustomTypeStatement(
        typeElement,
        ElementKind.ENUM,
        EnumStatements::build,
        typeContext,
        session
    );
  }

  private static EnumStatement build(TypeElement typeElement, TypeContext typeContext, Session session) {
    return new EnumStatementBasedOnTypeElement(typeElement, typeContext, session);
  }
}
