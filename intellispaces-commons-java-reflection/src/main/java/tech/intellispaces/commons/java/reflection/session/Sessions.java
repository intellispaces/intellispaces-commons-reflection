package tech.intellispaces.commons.java.reflection.session;

public interface Sessions {

  static Session get() {
    return new SessionImpl();
  }
}
