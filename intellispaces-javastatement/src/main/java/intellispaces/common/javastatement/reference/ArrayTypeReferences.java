package intellispaces.common.javastatement.reference;

import intellispaces.common.javastatement.context.TypeContext;
import intellispaces.common.javastatement.context.TypeContexts;
import intellispaces.common.javastatement.session.Session;

public interface ArrayTypeReferences {

  static ArrayReference of(javax.lang.model.type.ArrayType arrayType, Session session) {
    return of(arrayType, TypeContexts.empty(), session);
  }

  static ArrayReference of(javax.lang.model.type.ArrayType arrayType, TypeContext typeContext, Session session) {
    return new ArrayReferenceBasedOnArrayType(arrayType, typeContext, session);
  }
}
