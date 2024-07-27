package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.context.TypeContexts;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.common.TypeElementFunctions;
import tech.intellispaces.framework.javastatements.statement.type.NotPrimitiveType;

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
    return TypeElementFunctions.asCustomStatement(
        typeElement,
        ElementKind.CLASS,
        ClassStatements::create,
        typeContext,
        session
    );
  }

  static ClassStatement effectiveOf(
      ClassStatement classStatement, Map<String, NotPrimitiveType> typeMapping
  ) {
    return new EffectiveClassStatement(classStatement, typeMapping);
  }

  private static ClassStatement create(
      TypeElement typeElement, TypeContext typeContext, Session session
  ) {
    return new ClassStatementBasedOnStatementElement(typeElement, typeContext, session);
  }
}
