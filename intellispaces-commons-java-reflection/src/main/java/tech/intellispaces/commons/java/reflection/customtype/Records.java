package tech.intellispaces.commons.java.reflection.customtype;

import tech.intellispaces.commons.java.reflection.common.JavaModelFunctions;
import tech.intellispaces.commons.java.reflection.context.TypeContext;
import tech.intellispaces.commons.java.reflection.context.TypeContexts;
import tech.intellispaces.commons.java.reflection.session.Session;

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
