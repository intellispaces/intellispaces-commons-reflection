package tech.intellispaces.framework.javastatements.statement.reference;

import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.session.Session;

import javax.lang.model.type.ArrayType;

public interface ArrayTypeReferenceBuilder {

  static ArrayTypeReference build(ArrayType arrayType, TypeContext typeContext, Session session) {
    return new ArrayTypeReferenceAdapter(arrayType, typeContext, session);
  }
}
