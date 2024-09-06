package intellispaces.common.javastatement.reference;

import intellispaces.common.javastatement.context.TypeContext;
import intellispaces.common.javastatement.session.Session;

public interface WildcardTypes {

  static WildcardReference of(
      javax.lang.model.type.WildcardType wildcardType, TypeContext typeContext, Session session
  ) {
    return new WildcardReferenceBasedOnWildcardType(wildcardType, typeContext, session);
  }
}
