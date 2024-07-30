package tech.intellispaces.javastatements.reference;

import tech.intellispaces.javastatements.context.TypeContext;
import tech.intellispaces.javastatements.session.Session;

import javax.lang.model.element.TypeParameterElement;

public interface NamedTypes {

  static NamedReference of(TypeParameterElement typeParameter, TypeContext typeContext, Session session) {
    return new NamedReferenceBasedOnTypeParameterElement(typeParameter, typeContext, session);
  }
}
