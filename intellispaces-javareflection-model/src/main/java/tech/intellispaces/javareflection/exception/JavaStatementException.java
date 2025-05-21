package tech.intellispaces.javareflection.exception;

import tech.intellispaces.commons.exception.UnexpectedException;

public class JavaStatementException extends UnexpectedException {

  public JavaStatementException(String message) {
    super(message);
  }

  public JavaStatementException(String message, Throwable cause) {
    super(message, cause);
  }
}
