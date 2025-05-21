package tech.intellispaces.javareflection.session;

public interface Sessions {

  static Session get() {
    return new SessionImpl();
  }
}
