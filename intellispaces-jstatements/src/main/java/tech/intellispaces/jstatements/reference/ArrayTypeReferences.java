package tech.intellispaces.jstatements.reference;

import tech.intellispaces.jstatements.context.TypeContext;
import tech.intellispaces.jstatements.context.TypeContexts;
import tech.intellispaces.jstatements.session.Session;

public interface ArrayTypeReferences {

  static ArrayReference of(Class<?> elementClass) {
    return new ArrayReferenceBasedOnElementClass(elementClass);
  }

  static ArrayReference of(javax.lang.model.type.ArrayType arrayType, Session session) {
    return of(arrayType, TypeContexts.empty(), session);
  }

  static ArrayReference of(javax.lang.model.type.ArrayType arrayType, TypeContext typeContext, Session session) {
    return new ArrayReferenceBasedOnArrayType(arrayType, typeContext, session);
  }
}
