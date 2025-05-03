package tech.intellispaces.jstatements.samples;

import java.util.Collection;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public record RecordWithMethodUsingWildcardThatExtendsOtherClass() {

  public void methodUsingWildcardThatExtendsOtherClass(Collection<? extends Number> arg) {
  }
}