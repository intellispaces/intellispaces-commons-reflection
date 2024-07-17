package tech.intellispaces.framework.javastatements.statement.reference;

import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.session.Session;

import javax.lang.model.type.WildcardType;

public interface WildcardTypeReferences {

  static WildcardTypeReference of(WildcardType wildcardType, TypeContext typeContext, Session session) {
    return new WildcardTypeReferenceBasedOnWildcardType(wildcardType, typeContext, session);
  }
}
