package intellispaces.common.javastatements.context;

public final class TypeContexts {

  private TypeContexts() {}

  public static TypeContext empty() {
    return EMPTY_CONTEXT;
  }

  public static TypeContextBlank blank() {
    return new TypeContextImpl();
  }

  public static TypeContextBuilder build() {
    return new TypeContextBuilder();
  }

  private static final TypeContextEmpty EMPTY_CONTEXT = new TypeContextEmpty();
}
