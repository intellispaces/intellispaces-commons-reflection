package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.context.TypeContexts;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.common.TypeElementFunctions;
import tech.intellispaces.framework.javastatements.statement.reference.NonPrimitiveTypeReference;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.util.Map;

public interface InterfaceStatements {

  static InterfaceStatement of(Class<?> aClass) {
    return new InterfaceStatementBasedOnLangClass(aClass);
  }

  static InterfaceStatement of(TypeElement typeElement, Session session) {
    return of(typeElement, TypeContexts.empty(), session);
  }

  static InterfaceStatement of(TypeElement typeElement, TypeContext typeContext, Session session) {
    return TypeElementFunctions.asCustomTypeStatement(
        typeElement,
        ElementKind.INTERFACE,
        InterfaceStatements::build,
        typeContext,
        session
    );
  }

  static InterfaceStatement effectiveOf(
      InterfaceStatement actualType, Map<String, NonPrimitiveTypeReference> typeMapping
  ) {
    return new EffectiveInterfaceStatement(actualType, typeMapping);
  }

  private static InterfaceStatement build(TypeElement typeElement, TypeContext typeContext, Session session) {
    return new InterfaceStatementBasedOnTypeElement(typeElement, typeContext, session);
  }
}
