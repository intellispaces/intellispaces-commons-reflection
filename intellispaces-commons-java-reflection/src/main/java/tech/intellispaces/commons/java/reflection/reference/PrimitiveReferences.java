package tech.intellispaces.commons.java.reflection.reference;

import tech.intellispaces.commons.exception.UnexpectedExceptions;
import tech.intellispaces.commons.type.PrimitiveType;
import tech.intellispaces.commons.type.PrimitiveTypes;
import tech.intellispaces.commons.java.reflection.StatementType;
import tech.intellispaces.commons.java.reflection.StatementTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Primitive type reference provider.
 */
public enum PrimitiveReferences implements PrimitiveReference {

  Boolean(PrimitiveTypes.Boolean),

  Byte(PrimitiveTypes.Byte),

  Short(PrimitiveTypes.Short),

  Int(PrimitiveTypes.Int),

  Long(PrimitiveTypes.Long),

  Float(PrimitiveTypes.Float),

  Double(PrimitiveTypes.Double),

  Char(PrimitiveTypes.Char);

  private final PrimitiveType primitiveType;

  public static PrimitiveReference get(String typename) {
    PrimitiveReference primitive = VALUES.get(typename);
    if (primitive == null) {
      throw UnexpectedExceptions.withMessage("Not primitive typename: {}", typename);
    }
    return primitive;
  }

  PrimitiveReferences(PrimitiveType primitiveType) {
    this.primitiveType = primitiveType;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.PrimitiveReference;
  }

  @Override
  public String prettyDeclaration() {
    return primitiveType.typename();
  }

  @Override
  public PrimitiveType primitiveType() {
    return primitiveType;
  }

  @Override
  public boolean isVoidType() {
    return false;
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
    return typename();
  }

  @Override
  public String typename() {
    return primitiveType.typename();
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
