package intellispaces.common.javastatement.reference;

import intellispaces.common.base.exception.UnexpectedViolationException;
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
    PrimitiveReference primitive = CACHE.get(typename);
    if (primitive == null) {
      throw UnexpectedViolationException.withMessage("Not primitive: {}", typename);
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

  private static final Map<String, PrimitiveReference> CACHE = new HashMap<>();
  static {
    CACHE.put("boolean", Boolean);
    CACHE.put("char", Char);
    CACHE.put("byte", Byte);
    CACHE.put("short", Short);
    CACHE.put("long", Long);
    CACHE.put("int", Int);
    CACHE.put("float", Float);
    CACHE.put("double", Double);
  }
}
