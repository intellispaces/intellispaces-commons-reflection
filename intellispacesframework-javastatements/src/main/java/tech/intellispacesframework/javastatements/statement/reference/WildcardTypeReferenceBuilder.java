package tech.intellispacesframework.javastatements.statement.reference;

import tech.intellispacesframework.javastatements.context.TypeContext;
import tech.intellispacesframework.javastatements.session.Session;

import javax.lang.model.type.WildcardType;

public interface WildcardTypeReferenceBuilder {

  static WildcardTypeReference build(WildcardType wildcardType, TypeContext typeContext, Session session) {
    return new WildcardTypeReferenceAdapter(wildcardType, typeContext, session);
  }
}
