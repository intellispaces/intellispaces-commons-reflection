package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.context.TypeContexts;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.TypeElementFunctions;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

public interface AnnotationStatements {

  static AnnotationStatement of(TypeElement typeElement, Session session) {
    return of(typeElement, TypeContexts.empty(), session);
  }

  static AnnotationStatement of(TypeElement typeElement, TypeContext typeContext, Session session) {
    return TypeElementFunctions.asCustomTypeStatement(
        typeElement,
        ElementKind.ANNOTATION_TYPE,
        AnnotationStatements::build,
        typeContext,
        session
    );
  }

  private static AnnotationStatement build(TypeElement typeElement, TypeContext typeContext, Session session) {
    return new AnnotationStatementBasedOnTypeElement(typeElement, typeContext, session);
  }
}
