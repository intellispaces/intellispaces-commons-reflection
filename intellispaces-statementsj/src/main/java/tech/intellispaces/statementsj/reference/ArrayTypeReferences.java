package tech.intellispaces.statementsj.reference;

import tech.intellispaces.statementsj.context.TypeContext;
import tech.intellispaces.statementsj.context.TypeContexts;
import tech.intellispaces.statementsj.session.Session;

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
