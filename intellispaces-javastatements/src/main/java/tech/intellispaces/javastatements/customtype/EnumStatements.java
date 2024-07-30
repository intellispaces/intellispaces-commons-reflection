package tech.intellispaces.javastatements.customtype;

import tech.intellispaces.javastatements.context.TypeContext;
import tech.intellispaces.javastatements.context.TypeContexts;
import tech.intellispaces.javastatements.session.Session;
import tech.intellispaces.javastatements.common.JavaModelFunctions;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

public interface EnumStatements {

  static EnumType of(TypeElement typeElement, Session session) {
    return of(typeElement, TypeContexts.empty(), session);
  }

  static EnumType of(TypeElement typeElement, TypeContext typeContext, Session session) {
    return JavaModelFunctions.asCustomStatement(
        typeElement,
        ElementKind.ENUM,
        EnumStatements::create,
        typeContext,
        session
    );
  }

  private static EnumType create(
      TypeElement typeElement, TypeContext typeContext, Session session
  ) {
    return new EnumStatementBasedOnTypeElement(typeElement, typeContext, session);
  }
}
