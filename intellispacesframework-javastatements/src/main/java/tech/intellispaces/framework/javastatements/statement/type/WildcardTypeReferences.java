package tech.intellispaces.framework.javastatements.statement.type;

import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.session.Session;

public interface WildcardTypeReferences {

  static WildcardType of(javax.lang.model.type.WildcardType wildcardType, TypeContext typeContext, Session session) {
    return new WildcardTypeReferenceBasedOnWildcardType(wildcardType, typeContext, session);
  }
}
