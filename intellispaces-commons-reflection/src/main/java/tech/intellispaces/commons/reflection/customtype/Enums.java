package tech.intellispaces.commons.reflection.customtype;

import tech.intellispaces.commons.reflection.common.JavaModelFunctions;
import tech.intellispaces.commons.reflection.context.TypeContext;
import tech.intellispaces.commons.reflection.context.TypeContexts;
import tech.intellispaces.commons.reflection.session.Session;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

public interface Enums {

  static EnumType of(TypeElement typeElement, Session session) {
    return of(typeElement, TypeContexts.empty(), session);
  }

  static EnumType of(TypeElement typeElement, TypeContext typeContext, Session session) {
    return JavaModelFunctions.asCustomStatement(
        typeElement,
        ElementKind.ENUM,
        Enums::create,
        typeContext,
        session
    );
  }

  private static EnumType create(
      TypeElement typeElement, TypeContext typeContext, Session session
  ) {
    return new EnumBasedOnTypeElement(typeElement, typeContext, session);
  }
}
