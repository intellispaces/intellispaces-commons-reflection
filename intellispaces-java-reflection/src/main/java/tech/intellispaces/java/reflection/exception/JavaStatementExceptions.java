package tech.intellispaces.java.reflection.exception;

import tech.intellispaces.entity.text.StringFunctions;

/**
 * Provider of the exception {@link JavaStatementException}.
 */
public interface JavaStatementExceptions {

  static JavaStatementException withMessage(String message) {
    return new JavaStatementException(message);
  }

  static JavaStatementException withMessage(String template, Object... params) {
    return new JavaStatementException(StringFunctions.resolveTemplate(template, params));
  }

  static JavaStatementException withCauseAndMessage(Throwable cause, String message) {
    return new JavaStatementException(message, cause);
  }

  static JavaStatementException withCauseAndMessage(
      Throwable cause, String template, Object... params
  ) {
    return new JavaStatementException(StringFunctions.resolveTemplate(template, params), cause);
  }
}
