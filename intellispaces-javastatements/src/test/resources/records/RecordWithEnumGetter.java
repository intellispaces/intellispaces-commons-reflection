package intellispaces.javastatements.samples;

import intellispaces.javastatements.samples.TestEnum;
import intellispaces.javastatements.support.TesteeType;

@TesteeType
public record RecordWithEnumGetter() {

  public TestEnum enumGetter() {
    return null;
  }
}