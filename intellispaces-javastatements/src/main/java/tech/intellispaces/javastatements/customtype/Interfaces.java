package tech.intellispaces.javastatements.customtype;

import tech.intellispaces.javastatements.common.JavaModelFunctions;
import tech.intellispaces.javastatements.context.TypeContext;
import tech.intellispaces.javastatements.context.TypeContexts;
import tech.intellispaces.javastatements.reference.NotPrimitiveReference;
import tech.intellispaces.javastatements.session.Session;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.util.Map;

public interface Interfaces {

  static InterfaceType of(Class<?> aClass) {
    return new InterfaceTypeBasedOnLangClass(aClass);
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
    return new InterfaceStatementBasedOnTypeElement(typeElement, typeContext, session);
  }

  static InterfaceBuilderBasedOnPrototype build(InterfaceType prototype) {
    return new InterfaceBuilderBasedOnPrototype(prototype);
  }

  static InterfaceType effectiveOf(
      InterfaceType actualType, Map<String, NotPrimitiveReference> typeMapping
  ) {
    return new EffectiveInterfaceType(actualType, typeMapping);
  }
}
