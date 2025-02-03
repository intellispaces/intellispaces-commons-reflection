package tech.intellispaces.commons.java.reflection.samples;

import tech.intellispaces.commons.java.reflection.support.TesteeType;

public class GenericRecordWithCyclicTypeDependencyCase2 {

  @TesteeType
  public record RecordA<T1 extends RecordB<?>>() {}

  public record RecordB<T2 extends RecordA<?>>() {}
}
