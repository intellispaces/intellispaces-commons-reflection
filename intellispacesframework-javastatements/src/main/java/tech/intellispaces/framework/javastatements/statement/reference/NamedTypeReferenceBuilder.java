package tech.intellispaces.framework.javastatements.statement.reference;

import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.session.Session;

import javax.lang.model.element.TypeParameterElement;

public interface NamedTypeReferenceBuilder {

  static NamedTypeReference build(TypeParameterElement typeParameter, TypeContext typeContext, Session session) {
    return new NamedTypeReferenceAdapter(typeParameter, typeContext, session);
  }
}
