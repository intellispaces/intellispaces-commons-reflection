package intellispaces.common.javastatement.samples;

import intellispaces.common.javastatement.support.TesteeType;

@TesteeType
public record RecordWithIntGetter() {

  public int intGetter() {
    return 0;
  }
}