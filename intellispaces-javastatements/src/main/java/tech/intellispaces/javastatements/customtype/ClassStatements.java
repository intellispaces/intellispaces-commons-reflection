package tech.intellispaces.javastatements.customtype;

import tech.intellispaces.javastatements.context.TypeContext;
import tech.intellispaces.javastatements.context.TypeContexts;
import tech.intellispaces.javastatements.session.Session;
import tech.intellispaces.javastatements.common.JavaModelFunctions;
import tech.intellispaces.javastatements.reference.NotPrimitiveTypeReference;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.util.Map;

public interface ClassStatements {

  static ClassType of(Class<?> aClass) {
    return new ClassTypeBasedOnLangClass(aClass);
  }

  static ClassType of(TypeElement typeElement, Session session) {
    return of(typeElement, TypeContexts.empty(), session);
  }

  static ClassType of(TypeElement typeElement, TypeContext typeContext, Session session) {
    return JavaModelFunctions.asCustomStatement(
        typeElement,
        ElementKind.CLASS,
        ClassStatements::create,
        typeContext,
        session
    );
  }

  static ClassType effectiveOf(
      ClassType classStatement, Map<String, NotPrimitiveTypeReference> typeMapping
  ) {
    return new EffectiveClassType(classStatement, typeMapping);
  }

  private static ClassType create(
      TypeElement typeElement, TypeContext typeContext, Session session
  ) {
    return new ClassStatementBasedOnTypeElement(typeElement, typeContext, session);
  }
}
