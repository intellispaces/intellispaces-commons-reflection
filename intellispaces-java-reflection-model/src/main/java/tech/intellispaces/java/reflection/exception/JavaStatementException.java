package tech.intellispaces.java.reflection.exception;

import tech.intellispaces.general.exception.UnexpectedException;

public class JavaStatementException extends UnexpectedException {

  public JavaStatementException(String message) {
    super(message);
  }

  public JavaStatementException(String message, Throwable cause) {
    super(message, cause);
  }
}
