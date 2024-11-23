package tech.intellispaces.java.reflection.session;

public interface Sessions {

  static Session get() {
    return new SessionImpl();
  }
}
