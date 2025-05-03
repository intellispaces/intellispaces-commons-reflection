package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

public class GenericRecordWithCyclicTypeDependencyCase2 {

  @TesteeType
  public record RecordA<T1 extends RecordB<?>>() {}

  public record RecordB<T2 extends RecordA<?>>() {}
}
