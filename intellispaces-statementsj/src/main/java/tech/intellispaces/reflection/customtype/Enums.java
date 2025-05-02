package tech.intellispaces.statementsj.customtype;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

import tech.intellispaces.statementsj.common.JavaModelFunctions;
import tech.intellispaces.statementsj.context.TypeContext;
import tech.intellispaces.statementsj.context.TypeContexts;
import tech.intellispaces.statementsj.session.Session;

public interface Enums {

  static EnumType of(Class<?> enumClass) {
    return new EnumBasedOnClass(enumClass);
  }

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
