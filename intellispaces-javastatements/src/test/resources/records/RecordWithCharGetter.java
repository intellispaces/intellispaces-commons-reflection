package intellispaces.javastatements.samples;

import intellispaces.javastatements.support.TesteeType;

@TesteeType
public record RecordWithCharGetter() {

  public char charGetter() {
    return 'a';
  }
}