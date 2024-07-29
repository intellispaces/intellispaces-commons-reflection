package tech.intellispaces.javastatements.session;

public interface Sessions {

  static Session get() {
    return new SessionImpl();
  }
}
