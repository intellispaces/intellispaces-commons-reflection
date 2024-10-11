package intellispaces.common.javastatement.reference;

import intellispaces.common.base.type.Primitive;
import intellispaces.common.base.type.Primitives;
import intellispaces.common.javastatement.StatementType;
import intellispaces.common.javastatement.StatementTypes;

import java.util.function.Function;

public enum PrimitiveReferences implements PrimitiveReference {

  Byte(Primitives.Byte),

  Short(Primitives.Short),

  Int(Primitives.Int),

  Long(Primitives.Long),

  Float(Primitives.Float),

  Double(Primitives.Double),

  Char(Primitives.Char),

  Boolean(Primitives.Boolean);

  private final Primitive primitive;

  PrimitiveReferences(Primitive primitive) {
    this.primitive = primitive;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.PrimitiveReference;
  }

  public String typename() {
    return primitive.typename();
  }

  @Override
  public Class<?> wrapperClass() {
    return primitive.wrapperClass();
  }

  @Override
  public Primitive asPrimitive() {
    return primitive;
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
}
