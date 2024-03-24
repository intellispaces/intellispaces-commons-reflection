package intellispaces.javastatements.object.reference;

import intellispaces.javastatements.model.reference.PrimitiveTypeReference;

public enum PrimitiveTypeReferences implements PrimitiveTypeReference {

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

  public String typename() {
    return typename;
  }

  @Override
  public Class<?> wrapperClass() {
    return wrapperClass;
  }

  @Override
  public String referenceDeclaration() {
    return typename;
  }

  @Override
  public String typeFullDeclaration() {
    return typename;
  }

  @Override
  public String typeBriefDeclaration() {
    return typename;
  }

  @Override
  public String toString() {
    return referenceDeclaration();
  }
}
