package intellispaces.common.javastatement.reference;

import intellispaces.common.javastatement.context.TypeContext;
import intellispaces.common.javastatement.session.Session;

import javax.lang.model.element.TypeParameterElement;

public interface NamedTypes {

  static NamedReference of(TypeParameterElement typeParameter, TypeContext typeContext, Session session) {
    return new NamedReferenceBasedOnTypeParameterElement(typeParameter, typeContext, session);
  }

  static NamedReferencePrototypeBuilder build(NamedReference prototype) {
    return new NamedReferencePrototypeBuilder(prototype);
  }
}
