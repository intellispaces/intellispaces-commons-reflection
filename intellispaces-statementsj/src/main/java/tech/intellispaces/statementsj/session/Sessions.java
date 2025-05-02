package tech.intellispaces.statementsj.session;

public interface Sessions {

  static Session get() {
    return new SessionImpl();
  }
}
