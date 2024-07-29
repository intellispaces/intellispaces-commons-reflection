package tech.intellispaces.javastatements.statement;

import tech.intellispaces.commons.exception.UnexpectedViolationException;
import tech.intellispaces.javastatements.session.Sessions;
import tech.intellispaces.javastatements.statement.common.TypeElementFunctions;
import tech.intellispaces.javastatements.statement.method.MethodFunctions;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

public interface Statements {

  static Statement of(Element element) {
    if (element instanceof TypeElement typeElement) {
      return TypeElementFunctions.asCustomStatement(typeElement, Sessions.get());
    } else if (element instanceof ExecutableElement executableElement) {
      return MethodFunctions.getMethod(executableElement, Sessions.get());
    } else {
      throw UnexpectedViolationException.withMessage("Not supported element kind - {}", element.getKind());
    }
  }
}
