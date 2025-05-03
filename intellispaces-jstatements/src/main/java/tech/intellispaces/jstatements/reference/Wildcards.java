package tech.intellispaces.jstatements.reference;

import tech.intellispaces.jstatements.context.TypeContext;
import tech.intellispaces.jstatements.session.Session;

public interface Wildcards {

  static WildcardReference get(ReferenceBound extendedBound) {
    return new WildcardReferenceImpl(extendedBound, null);
  }

  static WildcardReference of(
      javax.lang.model.type.WildcardType wildcardType, TypeContext typeContext, Session session
  ) {
    return new WildcardReferenceBasedOnWildcardType(wildcardType, typeContext, session);
  }
}
