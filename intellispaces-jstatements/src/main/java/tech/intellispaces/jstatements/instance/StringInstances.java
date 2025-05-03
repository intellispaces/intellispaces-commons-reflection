package tech.intellispaces.jstatements.instance;

public interface StringInstances {

  static StringInstance of(String string) {
    return new StringInstanceImpl(string);
  }
}
