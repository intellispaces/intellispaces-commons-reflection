package tech.intellispaces.jstatements.reference;

import javax.lang.model.element.TypeParameterElement;

import tech.intellispaces.jstatements.context.TypeContext;
import tech.intellispaces.jstatements.session.Session;

public interface NamedTypes {

  static NamedReference of(TypeParameterElement typeParameter, TypeContext typeContext, Session session) {
    return new NamedReferenceBasedOnTypeParameterElement(typeParameter, typeContext, session);
  }

  static NamedReferencePrototypeBuilder build(NamedReference prototype) {
    return new NamedReferencePrototypeBuilder(prototype);
  }
}
