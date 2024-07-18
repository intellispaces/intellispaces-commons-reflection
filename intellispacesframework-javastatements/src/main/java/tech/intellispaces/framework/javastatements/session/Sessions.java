package tech.intellispaces.framework.javastatements.session;

public interface Sessions {

  static Session get() {
    return new SessionImpl();
  }
}
