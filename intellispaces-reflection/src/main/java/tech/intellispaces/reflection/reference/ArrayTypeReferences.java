package tech.intellispaces.reflection.reference;

import tech.intellispaces.reflection.context.TypeContext;
import tech.intellispaces.reflection.context.TypeContexts;
import tech.intellispaces.reflection.session.Session;

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
