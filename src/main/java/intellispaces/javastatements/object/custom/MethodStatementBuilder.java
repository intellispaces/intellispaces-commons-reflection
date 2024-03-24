package intellispaces.javastatements.object.custom;

import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.custom.CustomType;
import intellispaces.javastatements.model.custom.MethodSignature;
import intellispaces.javastatements.model.custom.MethodStatement;
import intellispaces.javastatements.model.session.Session;

import javax.lang.model.element.ExecutableElement;

public interface MethodStatementBuilder {

  static MethodStatement build(CustomType holder, MethodSignature signature) {
    return new MethodStatementObject(holder, signature);
  }

  static MethodStatement build(ExecutableElement executableElement, CustomType holder, NameContext nameContext, Session session) {
    return new MethodStatementAdapter(executableElement, holder, nameContext, session);
  }
}
