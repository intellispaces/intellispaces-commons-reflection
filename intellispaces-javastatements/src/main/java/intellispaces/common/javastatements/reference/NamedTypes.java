package intellispaces.common.javastatements.reference;

import intellispaces.common.javastatements.context.TypeContext;
import intellispaces.common.javastatements.session.Session;

import javax.lang.model.element.TypeParameterElement;

public interface NamedTypes {

  static NamedReference of(TypeParameterElement typeParameter, TypeContext typeContext, Session session) {
    return new NamedReferenceBasedOnTypeParameterElement(typeParameter, typeContext, session);
  }

  static NamedReferencePrototypeBuilder build(NamedReference prototype) {
    return new NamedReferencePrototypeBuilder(prototype);
  }
}
