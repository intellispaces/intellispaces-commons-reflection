package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

import java.io.IOException;

@TesteeType
public enum EnumWithMethodThrowsTwoExceptions {
  ;

  public void methodThrowsTwoExceptions() throws IOException, ClassNotFoundException {
  }
}