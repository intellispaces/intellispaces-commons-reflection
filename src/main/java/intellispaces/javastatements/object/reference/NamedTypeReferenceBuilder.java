package intellispaces.javastatements.object.reference;

import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.reference.NamedTypeReference;
import intellispaces.javastatements.model.session.Session;

import javax.lang.model.element.TypeParameterElement;

public interface NamedTypeReferenceBuilder {

  static NamedTypeReference build(TypeParameterElement typeParameter, NameContext nameContext, Session session) {
    return new NamedTypeReferenceAdapter(typeParameter, nameContext, session);
  }
}
