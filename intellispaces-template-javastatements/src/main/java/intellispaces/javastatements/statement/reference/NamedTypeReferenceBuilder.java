package intellispaces.javastatements.statement.reference;

import intellispaces.javastatements.context.TypeContext;
import intellispaces.javastatements.session.Session;

import javax.lang.model.element.TypeParameterElement;

public interface NamedTypeReferenceBuilder {

  static NamedTypeReference build(TypeParameterElement typeParameter, TypeContext typeContext, Session session) {
    return new NamedTypeReferenceAdapter(typeParameter, typeContext, session);
  }
}
