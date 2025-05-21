package tech.intellispaces.javareflection.samples;

import tech.intellispaces.javareflection.support.TesteeType;

public class GenericRecordWithCyclicTypeDependencyCase2 {

  @TesteeType
  public record RecordA<T1 extends RecordB<?>>() {}

  public record RecordB<T2 extends RecordA<?>>() {}
}
