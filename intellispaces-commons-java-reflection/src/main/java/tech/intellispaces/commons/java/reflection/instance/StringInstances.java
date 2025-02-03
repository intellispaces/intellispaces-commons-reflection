package tech.intellispaces.commons.java.reflection.instance;

public interface StringInstances {

  static StringInstance of(String string) {
    return new StringInstanceImpl(string);
  }
}
