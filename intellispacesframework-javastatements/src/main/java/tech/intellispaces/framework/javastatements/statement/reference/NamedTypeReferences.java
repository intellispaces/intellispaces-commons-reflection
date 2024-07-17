package tech.intellispaces.framework.javastatements.statement.reference;

import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.session.Session;

import javax.lang.model.element.TypeParameterElement;

public interface NamedTypeReferences {

  static NamedTypeReference of(TypeParameterElement typeParameter, TypeContext typeContext, Session session) {
    return new NamedTypeReferenceBasedOnTypeParameterElement(typeParameter, typeContext, session);
  }
}
