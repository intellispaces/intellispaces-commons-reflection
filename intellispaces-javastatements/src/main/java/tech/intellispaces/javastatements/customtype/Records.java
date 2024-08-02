package tech.intellispaces.javastatements.customtype;

import tech.intellispaces.javastatements.common.JavaModelFunctions;
import tech.intellispaces.javastatements.context.TypeContext;
import tech.intellispaces.javastatements.context.TypeContexts;
import tech.intellispaces.javastatements.session.Session;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

public interface Records {

  static RecordType of(TypeElement typeElement, Session session) {
    return of(typeElement, TypeContexts.empty(), session);
  }

  static RecordType of(
      TypeElement typeElement, TypeContext typeContext, Session session
  ) {
    return JavaModelFunctions.asCustomStatement(
        typeElement,
        ElementKind.RECORD,
        Records::create,
        typeContext,
        session
    );
  }

  private static RecordType create(
      TypeElement typeElement, TypeContext typeContext, Session session
  ) {
    return new RecordBasedOnTypeElement(typeElement, typeContext, session);
  }
}
