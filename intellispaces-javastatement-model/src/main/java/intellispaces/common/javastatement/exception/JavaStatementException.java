package intellispaces.common.javastatement.exception;

import tech.intellispaces.entity.exception.UnexpectedException;

public class JavaStatementException extends UnexpectedException {

  public JavaStatementException(String message) {
    super(message);
  }

  public JavaStatementException(String message, Throwable cause) {
    super(message, cause);
  }
}
