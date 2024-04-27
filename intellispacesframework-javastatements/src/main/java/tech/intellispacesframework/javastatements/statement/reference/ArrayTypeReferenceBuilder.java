package tech.intellispacesframework.javastatements.statement.reference;

import tech.intellispacesframework.javastatements.context.TypeContext;
import tech.intellispacesframework.javastatements.session.Session;

import javax.lang.model.type.ArrayType;

public interface ArrayTypeReferenceBuilder {

  static ArrayTypeReference build(ArrayType arrayType, TypeContext typeContext, Session session) {
    return new ArrayTypeReferenceAdapter(arrayType, typeContext, session);
  }
}
