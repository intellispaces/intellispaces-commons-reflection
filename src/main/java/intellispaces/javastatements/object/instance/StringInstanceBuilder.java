package intellispaces.javastatements.object.instance;

import intellispaces.javastatements.model.instance.StringInstance;

public interface StringInstanceBuilder {

  static StringInstance build(String string) {
    return new StringInstanceObject(string);
  }
}
