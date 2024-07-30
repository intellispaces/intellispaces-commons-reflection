package tech.intellispaces.javastatements.reference;

import tech.intellispaces.javastatements.context.TypeContext;
import tech.intellispaces.javastatements.context.TypeContexts;
import tech.intellispaces.javastatements.session.Session;

public interface ArrayTypeReferences {

  static ArrayReference of(javax.lang.model.type.ArrayType arrayType, Session session) {
    return of(arrayType, TypeContexts.empty(), session);
  }

  static ArrayReference of(javax.lang.model.type.ArrayType arrayType, TypeContext typeContext, Session session) {
    return new ArrayTypeReferenceBasedOnArrayReference(arrayType, typeContext, session);
  }
}
