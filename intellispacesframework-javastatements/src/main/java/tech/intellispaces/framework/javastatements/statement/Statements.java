package tech.intellispaces.framework.javastatements.statement;

import tech.intellispaces.framework.commons.exception.UnexpectedViolationException;
import tech.intellispaces.framework.javastatements.session.Sessions;
import tech.intellispaces.framework.javastatements.statement.common.TypeElementFunctions;
import tech.intellispaces.framework.javastatements.statement.method.MethodFunctions;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

public interface Statements {

  static Statement of(Element element) {
    if (element instanceof TypeElement typeElement) {
      return TypeElementFunctions.asCustomTypeStatement(typeElement, Sessions.get());
    } else if (element instanceof ExecutableElement executableElement) {
      return MethodFunctions.getMethod(executableElement, Sessions.get());
    } else {
      throw UnexpectedViolationException.withMessage("Not supported element kind - {}", element.getKind());
    }
  }
}
