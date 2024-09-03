package intellispaces.common.javastatements.reference;

import intellispaces.common.javastatements.context.TypeContext;
import intellispaces.common.javastatements.context.TypeContexts;
import intellispaces.common.javastatements.session.Session;

public interface ArrayTypeReferences {

  static ArrayReference of(javax.lang.model.type.ArrayType arrayType, Session session) {
    return of(arrayType, TypeContexts.empty(), session);
  }

  static ArrayReference of(javax.lang.model.type.ArrayType arrayType, TypeContext typeContext, Session session) {
    return new ArrayReferenceBasedOnArrayType(arrayType, typeContext, session);
  }
}
