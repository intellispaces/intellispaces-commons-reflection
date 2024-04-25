package intellispaces.javastatements.statement.instance;

public interface StringInstanceBuilder {

  static StringInstance build(String string) {
    return new StringInstanceImpl(string);
  }
}
