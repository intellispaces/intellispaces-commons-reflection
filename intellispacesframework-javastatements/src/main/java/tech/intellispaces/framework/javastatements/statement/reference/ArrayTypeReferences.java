package tech.intellispaces.framework.javastatements.statement.reference;

import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.context.TypeContexts;
import tech.intellispaces.framework.javastatements.session.Session;

import javax.lang.model.type.ArrayType;

public interface ArrayTypeReferences {

  static ArrayTypeReference of(ArrayType arrayType, Session session) {
    return of(arrayType, TypeContexts.empty(), session);
  }

  static ArrayTypeReference of(ArrayType arrayType, TypeContext typeContext, Session session) {
    return new ArrayTypeReferenceBasedOnArrayType(arrayType, typeContext, session);
  }
}
