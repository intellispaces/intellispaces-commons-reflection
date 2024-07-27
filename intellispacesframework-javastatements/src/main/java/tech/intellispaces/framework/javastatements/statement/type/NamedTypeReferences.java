package tech.intellispaces.framework.javastatements.statement.type;

import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.session.Session;

import javax.lang.model.element.TypeParameterElement;

public interface NamedTypeReferences {

  static NamedType of(TypeParameterElement typeParameter, TypeContext typeContext, Session session) {
    return new NamedTypeReferenceBasedOnTypeParameterElement(typeParameter, typeContext, session);
  }
}
