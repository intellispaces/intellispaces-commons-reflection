package tech.intellispaces.statementsj.customtype;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

import tech.intellispaces.statementsj.common.JavaModelFunctions;
import tech.intellispaces.statementsj.context.TypeContext;
import tech.intellispaces.statementsj.context.TypeContexts;
import tech.intellispaces.statementsj.session.Session;

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
