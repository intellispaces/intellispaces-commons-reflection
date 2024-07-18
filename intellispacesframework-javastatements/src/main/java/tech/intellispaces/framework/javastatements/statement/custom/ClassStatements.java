package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.context.TypeContexts;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.common.TypeElementFunctions;
import tech.intellispaces.framework.javastatements.statement.reference.NonPrimitiveTypeReference;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.util.Map;

public interface ClassStatements {

  static ClassStatement of(Class<?> aClass) {
    return new ClassStatementBasedOnLangClass(aClass);
  }

  static ClassStatement of(TypeElement typeElement, Session session) {
    return of(typeElement, TypeContexts.empty(), session);
  }

  static ClassStatement of(TypeElement typeElement, TypeContext typeContext, Session session) {
    return TypeElementFunctions.asCustomTypeStatement(
        typeElement,
        ElementKind.CLASS,
        ClassStatements::build,
        typeContext,
        session
    );
  }

  static ClassStatement effectiveOf(ClassStatement classStatement, Map<String, NonPrimitiveTypeReference> typeMapping) {
    return new EffectiveClassStatement(classStatement, typeMapping);
  }

  private static ClassStatement build(TypeElement typeElement, TypeContext typeContext, Session session) {
    return new ClassStatementBasedOnTypeElement(typeElement, typeContext, session);
  }
}
