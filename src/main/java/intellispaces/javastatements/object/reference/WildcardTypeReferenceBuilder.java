package intellispaces.javastatements.object.reference;

import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.reference.WildcardTypeReference;
import intellispaces.javastatements.model.session.Session;

import javax.lang.model.type.WildcardType;

public interface WildcardTypeReferenceBuilder {

  static WildcardTypeReference build(WildcardType wildcardType, NameContext nameContext, Session session) {
    return new WildcardTypeReferenceAdapter(wildcardType, nameContext, session);
  }
}
