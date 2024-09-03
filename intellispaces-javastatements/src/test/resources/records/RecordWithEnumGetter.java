package intellispaces.common.javastatements.samples;

import intellispaces.common.javastatements.samples.TestEnum;
import intellispaces.common.javastatements.support.TesteeType;

@TesteeType
public record RecordWithEnumGetter() {

  public TestEnum enumGetter() {
    return null;
  }
}