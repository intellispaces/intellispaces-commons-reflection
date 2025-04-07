package tech.intellispaces.reflection.session;

public interface Sessions {

  static Session get() {
    return new SessionImpl();
  }
}
