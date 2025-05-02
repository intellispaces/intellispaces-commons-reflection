package tech.intellispaces.statementsj.reference;

import tech.intellispaces.statementsj.context.TypeContext;
import tech.intellispaces.statementsj.session.Session;

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
