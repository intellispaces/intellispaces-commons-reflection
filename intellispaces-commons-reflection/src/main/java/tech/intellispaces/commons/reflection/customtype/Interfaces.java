package tech.intellispaces.commons.reflection.customtype;

import tech.intellispaces.commons.reflection.common.JavaModelFunctions;
import tech.intellispaces.commons.reflection.context.TypeContext;
import tech.intellispaces.commons.reflection.context.TypeContexts;
import tech.intellispaces.commons.reflection.reference.NotPrimitiveReference;
import tech.intellispaces.commons.reflection.session.Session;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.util.Map;

public interface Interfaces {

  static InterfaceType of(Class<?> aClass) {
    return new InterfaceBasedOnLangClass(aClass);
  }

  static InterfaceType of(TypeElement typeElement, Session session) {
    return of(typeElement, TypeContexts.empty(), session);
  }

  static InterfaceType of(TypeElement typeElement, TypeContext typeContext, Session session) {
    return JavaModelFunctions.asCustomStatement(
        typeElement,
        ElementKind.INTERFACE,
        Interfaces::create,
        typeContext,
        session
    );
  }

  private static InterfaceType create(
      TypeElement typeElement, TypeContext typeContext, Session session
  ) {
    return new InterfaceBasedOnTypeElement(typeElement, typeContext, session);
  }

  static InterfacePrototypeBuilder build(InterfaceType prototype) {
    return new InterfacePrototypeBuilder(prototype);
  }

  static InterfaceType effectiveOf(
      InterfaceType actualType, Map<String, NotPrimitiveReference> typeMapping
  ) {
    return new EffectiveInterfaceType(actualType, typeMapping);
  }
}
