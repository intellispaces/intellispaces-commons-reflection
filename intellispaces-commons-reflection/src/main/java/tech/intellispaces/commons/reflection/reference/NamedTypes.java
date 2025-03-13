package tech.intellispaces.commons.reflection.reference;

import tech.intellispaces.commons.reflection.context.TypeContext;
import tech.intellispaces.commons.reflection.session.Session;

import javax.lang.model.element.TypeParameterElement;

public interface NamedTypes {

  static NamedReference of(TypeParameterElement typeParameter, TypeContext typeContext, Session session) {
    return new NamedReferenceBasedOnTypeParameterElement(typeParameter, typeContext, session);
  }

  static NamedReferencePrototypeBuilder build(NamedReference prototype) {
    return new NamedReferencePrototypeBuilder(prototype);
  }
}
