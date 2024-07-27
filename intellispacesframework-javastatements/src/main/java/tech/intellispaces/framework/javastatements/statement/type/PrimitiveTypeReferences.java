package tech.intellispaces.framework.javastatements.statement.type;

import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;

import java.util.function.Function;

public enum PrimitiveTypeReferences implements PrimitiveType {

  Byte("byte", java.lang.Byte.class),

  Short("short", java.lang.Short.class),

  Integer("int", java.lang.Integer.class),

  Long("long", java.lang.Long.class),

  Float("float", java.lang.Float.class),

  Double("double", java.lang.Double.class),

  Char("char", Character.class),

  Boolean("boolean", java.lang.Boolean.class);

  private final String typename;
  private final Class<?> wrapperClass;

  PrimitiveTypeReferences(String typename, Class<?> wrapperClass) {
    this.typename = typename;
    this.wrapperClass = wrapperClass;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.PrimitiveType;
  }

  public String typename() {
    return typename;
  }

  @Override
  public Class<?> wrapperClass() {
    return wrapperClass;
  }

  @Override
  public String actualDeclaration() {
    return typename;
  }

  @Override
  public String actualDeclaration(Function<String, String> simpleNameMapper) {
    return typename;
  }

  @Override
  public String actualBlindDeclaration(Function<String, String> simpleNameMapper) {
    return typename;
  }

  @Override
  public String formalFullDeclaration() {
    return typename;
  }

  @Override
  public String formalBriefDeclaration() {
    return typename;
  }

  @Override
  public String toString() {
    return actualDeclaration();
  }
}
