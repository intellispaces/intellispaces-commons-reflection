package tech.intellispaces.framework.javastatements.context;

public final class TypeContexts {

  public static TypeContext empty() {
    return EMPTY_CONTEXT;
  }

  public static TypeContextBlank blank() {
    return new TypeContextImpl();
  }

  public static TypeContextBuilder builder() {
    return new TypeContextBuilder();
  }

  private TypeContexts() {}

  private static final TypeContextEmpty EMPTY_CONTEXT = new TypeContextEmpty();
}
