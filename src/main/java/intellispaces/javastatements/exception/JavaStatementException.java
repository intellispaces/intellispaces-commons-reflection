package intellispaces.javastatements.exception;

import intellispaces.commons.exception.UnexpectedViolationException;

public class JavaStatementException extends UnexpectedViolationException {

  public JavaStatementException(String messageTemplate, Object... arguments) {
    super(messageTemplate, arguments);
  }

  public JavaStatementException(Throwable cause, String messageTemplate, Object... arguments) {
    super(cause, messageTemplate, arguments);
  }
}
