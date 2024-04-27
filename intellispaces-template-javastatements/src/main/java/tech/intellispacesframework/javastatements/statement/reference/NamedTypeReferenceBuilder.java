package tech.intellispacesframework.javastatements.statement.reference;

import tech.intellispacesframework.javastatements.context.TypeContext;
import tech.intellispacesframework.javastatements.session.Session;

import javax.lang.model.element.TypeParameterElement;

public interface NamedTypeReferenceBuilder {

  static NamedTypeReference build(TypeParameterElement typeParameter, TypeContext typeContext, Session session) {
    return new NamedTypeReferenceAdapter(typeParameter, typeContext, session);
  }
}
