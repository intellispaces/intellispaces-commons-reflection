package tech.intellispaces.javastatements.customtype;

import tech.intellispaces.javastatements.context.TypeContext;
import tech.intellispaces.javastatements.context.TypeContexts;
import tech.intellispaces.javastatements.session.Session;
import tech.intellispaces.javastatements.common.JavaModelFunctions;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

public interface AnnotationStatements {

  static AnnotationType of(TypeElement typeElement, Session session) {
    return of(typeElement, TypeContexts.empty(), session);
  }

  static AnnotationType of(TypeElement typeElement, TypeContext typeContext, Session session) {
    return JavaModelFunctions.asCustomStatement(
        typeElement,
        ElementKind.ANNOTATION_TYPE,
        AnnotationStatements::create,
        typeContext,
        session
    );
  }

  private static AnnotationType create(
      TypeElement typeElement, TypeContext typeContext, Session session
  ) {
    return new AnnotationStatementBasedOnTypeElement(typeElement, typeContext, session);
  }
}
