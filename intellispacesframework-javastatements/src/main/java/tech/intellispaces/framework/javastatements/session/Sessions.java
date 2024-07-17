package tech.intellispaces.framework.javastatements.session;

public interface Sessions {

  static Session create() {
    return new SessionImpl();
  }
}
