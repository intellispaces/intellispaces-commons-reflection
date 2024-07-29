package tech.intellispaces.javastatements.statement.instance;

public interface StringInstances {

  static StringInstance of(String string) {
    return new StringInstanceImpl(string);
  }
}
