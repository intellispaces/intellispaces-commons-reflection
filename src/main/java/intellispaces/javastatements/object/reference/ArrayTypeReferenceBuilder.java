package intellispaces.javastatements.object.reference;

import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.reference.ArrayTypeReference;
import intellispaces.javastatements.model.session.Session;

import javax.lang.model.type.ArrayType;

public interface ArrayTypeReferenceBuilder {

  static ArrayTypeReference build(ArrayType arrayType, NameContext nameContext, Session session) {
    return new ArrayTypeReferenceAdapter(arrayType, nameContext, session);
  }
}
