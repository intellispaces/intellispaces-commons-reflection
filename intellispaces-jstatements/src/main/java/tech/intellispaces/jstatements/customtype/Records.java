package tech.intellispaces.jstatements.customtype;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

import tech.intellispaces.jstatements.common.JavaModelFunctions;
import tech.intellispaces.jstatements.context.TypeContext;
import tech.intellispaces.jstatements.context.TypeContexts;
import tech.intellispaces.jstatements.session.Session;

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
