package tech.intellispaces.framework.javastatements.statement.type;

import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.context.TypeContexts;
import tech.intellispaces.framework.javastatements.session.Session;

public interface ArrayTypeReferences {

  static ArrayType of(javax.lang.model.type.ArrayType arrayType, Session session) {
    return of(arrayType, TypeContexts.empty(), session);
  }

  static ArrayType of(javax.lang.model.type.ArrayType arrayType, TypeContext typeContext, Session session) {
    return new ArrayTypeReferenceBasedOnArrayType(arrayType, typeContext, session);
  }
}
