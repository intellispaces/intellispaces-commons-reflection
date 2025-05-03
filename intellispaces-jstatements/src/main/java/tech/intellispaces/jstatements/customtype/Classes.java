package tech.intellispaces.jstatements.customtype;

import java.util.Map;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

import tech.intellispaces.jstatements.common.JavaModelFunctions;
import tech.intellispaces.jstatements.context.TypeContext;
import tech.intellispaces.jstatements.context.TypeContexts;
import tech.intellispaces.jstatements.reference.NotPrimitiveReference;
import tech.intellispaces.jstatements.session.Session;

public interface Classes {

  static ClassType of(Class<?> aClass) {
    return new ClassBasedOnLangClass(aClass);
  }

  static ClassType of(TypeElement typeElement, Session session) {
    return of(typeElement, TypeContexts.empty(), session);
  }

  static ClassType of(TypeElement typeElement, TypeContext typeContext, Session session) {
    return JavaModelFunctions.asCustomStatement(
        typeElement,
        ElementKind.CLASS,
        Classes::create,
        typeContext,
        session
    );
  }

  static ClassType effectiveOf(
      ClassType classStatement, Map<String, NotPrimitiveReference> typeMapping
  ) {
    return new EffectiveClassType(classStatement, typeMapping);
  }

  private static ClassType create(
      TypeElement typeElement, TypeContext typeContext, Session session
  ) {
    return new ClassBasedOnTypeElement(typeElement, typeContext, session);
  }

  static ClassTypeBuilder build() {
    return new ClassTypeBuilder();
  }
}
