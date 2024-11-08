package intellispaces.common.javastatement.reference;

import intellispaces.common.base.exception.UnexpectedExceptions;
import intellispaces.common.base.type.Primitive;
import intellispaces.common.base.type.Primitives;
import intellispaces.common.javastatement.StatementType;
import intellispaces.common.javastatement.StatementTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public enum PrimitiveReferences implements PrimitiveReference {

  Boolean(Primitives.Boolean),

  Byte(Primitives.Byte),

  Short(Primitives.Short),

  Int(Primitives.Int),

  Long(Primitives.Long),

  Float(Primitives.Float),

  Double(Primitives.Double),

  Char(Primitives.Char);

  private final Primitive primitive;

  public static PrimitiveReference get(String typename) {
    PrimitiveReference primitive = VALUES.get(typename);
    if (primitive == null) {
      throw UnexpectedExceptions.withMessage("Not primitive typename: {}", typename);
    }
    return primitive;
  }

  PrimitiveReferences(Primitive primitive) {
    this.primitive = primitive;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.PrimitiveReference;
  }

  @Override
  public String prettyDeclaration() {
    return primitive.typename();
  }

  @Override
  public boolean isChar() {
    return primitive.isChar();
  }

  @Override
  public boolean isBoolean() {
    return primitive.isBoolean();
  }

  @Override
  public boolean isByte() {
    return primitive.isByte();
  }

  @Override
  public boolean isShort() {
    return primitive.isShort();
  }

  @Override
  public boolean isInt() {
    return primitive.isInt();
  }

  @Override
  public boolean isLong() {
    return primitive.isLong();
  }

  @Override
  public boolean isFloat() {
    return primitive.isFloat();
  }

  @Override
  public boolean isDouble() {
    return primitive.isDouble();
  }

  public String typename() {
    return primitive.typename();
  }

  @Override
  public Class<?> wrapperClass() {
    return primitive.wrapperClass();
  }

  @Override
  public String simpleDeclaration() {
    return typename();
  }

  @Override
  public String simpleDeclaration(Function<String, String> nameMapper) {
    return typename();
  }

  @Override
  public String actualDeclaration() {
    return typename();
  }

  @Override
  public String actualDeclaration(Function<String, String> nameMapper) {
    return typename();
  }

  @Override
  public String actualBlindDeclaration(Function<String, String> nameMapper) {
    return typename();
  }

  @Override
  public String formalFullDeclaration() {
    return typename();
  }

  @Override
  public String formalBriefDeclaration() {
    return typename();
  }

  @Override
  public String toString() {
    return actualDeclaration();
  }

  private static final Map<String, PrimitiveReference> VALUES = new HashMap<>();
  static {
    VALUES.put("boolean", Boolean);
    VALUES.put("char", Char);
    VALUES.put("byte", Byte);
    VALUES.put("short", Short);
    VALUES.put("long", Long);
    VALUES.put("int", Int);
    VALUES.put("float", Float);
    VALUES.put("double", Double);
  }
}
