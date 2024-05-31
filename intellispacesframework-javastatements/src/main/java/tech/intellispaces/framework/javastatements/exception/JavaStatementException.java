package tech.intellispaces.framework.javastatements.exception;

import tech.intellispaces.framework.commons.exception.UnexpectedViolationException;

public class JavaStatementException extends UnexpectedViolationException {

  protected JavaStatementException(String messageTemplate, Object... arguments) {
    super(messageTemplate, arguments);
  }

  protected JavaStatementException(Throwable cause, String messageTemplate, Object... arguments) {
    super(cause, messageTemplate, arguments);
  }

  public static JavaStatementException withMessage(String messageTemplate, Object... arguments) {
    return new JavaStatementException(messageTemplate, arguments);
  }

  public static JavaStatementException withCauseAndMessage(Throwable cause, String messageTemplate, Object... arguments) {
    return new JavaStatementException(cause, messageTemplate, arguments);
  }
}
