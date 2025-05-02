package tech.intellispaces.statementsj.reference;

import javax.lang.model.element.TypeParameterElement;

import tech.intellispaces.statementsj.context.TypeContext;
import tech.intellispaces.statementsj.session.Session;

public interface NamedTypes {

  static NamedReference of(TypeParameterElement typeParameter, TypeContext typeContext, Session session) {
    return new NamedReferenceBasedOnTypeParameterElement(typeParameter, typeContext, session);
  }

  static NamedReferencePrototypeBuilder build(NamedReference prototype) {
    return new NamedReferencePrototypeBuilder(prototype);
  }
}
