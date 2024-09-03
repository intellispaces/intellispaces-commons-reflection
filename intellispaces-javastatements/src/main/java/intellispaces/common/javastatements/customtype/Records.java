package intellispaces.common.javastatements.customtype;

import intellispaces.common.javastatements.context.TypeContext;
import intellispaces.common.javastatements.common.JavaModelFunctions;
import intellispaces.common.javastatements.context.TypeContexts;
import intellispaces.common.javastatements.session.Session;

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
