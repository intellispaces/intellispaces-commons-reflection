package intellispaces.javastatements.customtype;

import intellispaces.javastatements.common.JavaModelFunctions;
import intellispaces.javastatements.context.TypeContext;
import intellispaces.javastatements.context.TypeContexts;
import intellispaces.javastatements.reference.NotPrimitiveReference;
import intellispaces.javastatements.session.Session;

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
}
