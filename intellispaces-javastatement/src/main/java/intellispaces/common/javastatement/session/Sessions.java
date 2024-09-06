package intellispaces.common.javastatement.session;

public interface Sessions {

  static Session get() {
    return new SessionImpl();
  }
}
