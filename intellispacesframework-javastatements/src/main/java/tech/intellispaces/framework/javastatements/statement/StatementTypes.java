package tech.intellispaces.framework.javastatements.statement;

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
   * Primitive type.
   */
  PrimitiveType,

  /**
   * Custom type.
   */
  CustomType,

  /**
   * Array type.
   */
  ArrayType,

  /**
   * Named type.
   */
  NamedType,

  /**
   * Wildcard type.
   */
  WildcardType;

  @Override
  public String typename() {
    return this.name();
  }
}
