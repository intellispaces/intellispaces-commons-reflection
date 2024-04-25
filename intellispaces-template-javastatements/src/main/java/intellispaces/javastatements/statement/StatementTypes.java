package intellispaces.javastatements.statement;

import intellispaces.javastatements.statement.StatementType;

public enum StatementTypes implements StatementType {

  /**
   * Class statement.
   */
  Class,

  /**
   * Interface statement.
   */
  Interface,

  /**
   * Record statement.
   */
  Record,

  /**
   * Annotation statement.
   */
  Annotation,

  /**
   * Enum statement.
   */
  Enum,

  /**
   * Primitive instance literal.
   */
  PrimitiveInstance,

  /**
   * String instance literal.
   */
  StringInstance,

  /**
   * Array instance literal.
   */
  ArrayInstance,

  /**
   * Class instance.
   */
  ClassInstance,

  /**
   * Enum instance.
   */
  EnumInstance,

  /**
   * Annotation instance.
   */
  AnnotationInstance,

  /**
   * Method statement.
   */
  Method,

  /**
   * Method signature statement.
   */
  MethodSignature,

  /**
   * Method parameter statement.
   */
  MethodParam,

  /**
   * Reference to primitive type.
   */
  PrimitiveReference,

  /**
   * Reference to custom type.
   */
  CustomTypeReference,

  /**
   * Reference to array.
   */
  ArrayTypeReference,

  /**
   * Named type reference.
   */
  NamedTypeReference,

  /**
   * Wildcard type reference.
   */
  WildcardTypeReference;

  @Override
  public String typename() {
    return this.name();
  }
}
