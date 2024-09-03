package intellispaces.common.javastatements.reference;

import intellispaces.common.javastatements.context.TypeContext;
import intellispaces.common.javastatements.session.Session;

public interface WildcardTypes {

  static WildcardReference of(
      javax.lang.model.type.WildcardType wildcardType, TypeContext typeContext, Session session
  ) {
    return new WildcardReferenceBasedOnWildcardType(wildcardType, typeContext, session);
  }
}
