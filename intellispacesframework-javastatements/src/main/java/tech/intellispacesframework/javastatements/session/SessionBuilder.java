package tech.intellispacesframework.javastatements.session;

public interface SessionBuilder {

  static Session buildSession() {
    return new SessionImpl();
  }
}
