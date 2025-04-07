package tech.intellispaces.reflection.reference;

import tech.intellispaces.reflection.context.TypeContext;
import tech.intellispaces.reflection.session.Session;

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
