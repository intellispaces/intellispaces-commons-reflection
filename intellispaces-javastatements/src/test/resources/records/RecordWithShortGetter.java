package intellispaces.javastatements.samples;

import intellispaces.javastatements.support.TesteeType;

@TesteeType
public record RecordWithShortGetter() {

  public short shortGetter() {
    return 0;
  }
}