package tech.intellispaces.commons.reflection.session;

public interface Sessions {

  static Session get() {
    return new SessionImpl();
  }
}
