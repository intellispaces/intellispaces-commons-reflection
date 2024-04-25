package intellispaces.javastatements.statement.reference;

import intellispaces.javastatements.context.TypeContext;
import intellispaces.javastatements.session.Session;

import javax.lang.model.type.WildcardType;

public interface WildcardTypeReferenceBuilder {

  static WildcardTypeReference build(WildcardType wildcardType, TypeContext typeContext, Session session) {
    return new WildcardTypeReferenceAdapter(wildcardType, typeContext, session);
  }
}
