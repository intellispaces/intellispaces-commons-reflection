package tech.intellispaces.framework.javastatements.session;

public interface SessionBuilder {

  static Session buildSession() {
    return new SessionImpl();
  }
}
