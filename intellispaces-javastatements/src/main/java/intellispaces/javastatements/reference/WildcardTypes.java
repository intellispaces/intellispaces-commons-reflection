package intellispaces.javastatements.reference;

import intellispaces.javastatements.context.TypeContext;
import intellispaces.javastatements.session.Session;

public interface WildcardTypes {

  static WildcardReference of(
      javax.lang.model.type.WildcardType wildcardType, TypeContext typeContext, Session session
  ) {
    return new WildcardReferenceBasedOnWildcardType(wildcardType, typeContext, session);
  }
}
