package tech.intellispaces.jstatements.session;

public interface Sessions {

  static Session get() {
    return new SessionImpl();
  }
}
