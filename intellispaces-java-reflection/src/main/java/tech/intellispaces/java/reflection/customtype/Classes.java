package tech.intellispaces.java.reflection.customtype;

import tech.intellispaces.java.reflection.common.JavaModelFunctions;
import tech.intellispaces.java.reflection.context.TypeContext;
import tech.intellispaces.java.reflection.context.TypeContexts;
import tech.intellispaces.java.reflection.reference.NotPrimitiveReference;
import tech.intellispaces.java.reflection.session.Session;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.util.Map;

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
