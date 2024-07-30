package tech.intellispaces.javastatements.reference;

import tech.intellispaces.javastatements.context.TypeContext;
import tech.intellispaces.javastatements.session.Session;

public interface WildcardTypes {

  static WildcardReference of(
      javax.lang.model.type.WildcardType wildcardType, TypeContext typeContext, Session session
  ) {
    return new WildcardReferenceBasedOnWildcardType(wildcardType, typeContext, session);
  }
}
