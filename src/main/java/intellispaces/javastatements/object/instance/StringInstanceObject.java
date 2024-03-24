package intellispaces.javastatements.object.instance;

import intellispaces.javastatements.model.instance.StringInstance;

class StringInstanceObject implements StringInstance {
  private final String string;

  StringInstanceObject(String string) {
    this.string = string;
  }

  @Override
  public String value() {
    return string;
  }
}
