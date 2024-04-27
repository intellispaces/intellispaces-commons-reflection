package tech.intellispacesframework.javastatements.sample;

import tech.intellispacesframework.javastatements.support.TesteeType;

import java.io.IOException;

@TesteeType
public enum EnumWithMethodThrowsTwoExceptions {
  ;

  public void methodThrowsTwoExceptions() throws IOException, ClassNotFoundException {
  }
}