package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.context.TypeContexts;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.common.TypeElementFunctions;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

public interface AnnotationStatements {

  static AnnotationStatement of(TypeElement typeElement, Session session) {
    return of(typeElement, TypeContexts.empty(), session);
  }

  static AnnotationStatement of(TypeElement typeElement, TypeContext typeContext, Session session) {
    return TypeElementFunctions.asCustomStatement(
        typeElement,
        ElementKind.ANNOTATION_TYPE,
        AnnotationStatements::create,
        typeContext,
        session
    );
  }

  private static AnnotationStatement create(
      TypeElement typeElement, TypeContext typeContext, Session session
  ) {
    return new AnnotationStatementBasedOnStatementElement(typeElement, typeContext, session);
  }
}
