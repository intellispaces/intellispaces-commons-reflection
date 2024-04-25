package intellispaces.javastatements.statement.reference;

import intellispaces.javastatements.context.TypeContext;
import intellispaces.javastatements.session.Session;

import javax.lang.model.type.ArrayType;

public interface ArrayTypeReferenceBuilder {

  static ArrayTypeReference build(ArrayType arrayType, TypeContext typeContext, Session session) {
    return new ArrayTypeReferenceAdapter(arrayType, typeContext, session);
  }
}
