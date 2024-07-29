package tech.intellispaces.javastatements.statement.reference;

import tech.intellispaces.javastatements.context.TypeContext;
import tech.intellispaces.javastatements.session.Session;

public interface WildcardTypes {

  static WildcardReference of(
      javax.lang.model.type.WildcardType wildcardType, TypeContext typeContext, Session session
  ) {
    return new WildcardTypeReferenceBasedOnWildcardReference(wildcardType, typeContext, session);
  }
}
