package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public record RecordWithByteGetter() {

  public byte byteGetter() {
    return 0;
  }
}