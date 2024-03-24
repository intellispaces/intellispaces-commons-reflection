package intellispaces.javastatements.object.custom;

import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.custom.ClassStatement;
import intellispaces.javastatements.model.session.Session;

import javax.lang.model.element.TypeElement;

public interface ClassStatementBuilder {

  static ClassStatement build(TypeElement typeElement, NameContext nameContext, Session session) {
    return new TypeElementToClassStatementAdapter(typeElement, nameContext, session);
  }

  static ClassStatement build(Class<?> aClass) {
    return new ClassToClassStatementAdapter(aClass);
  }
}
