package tech.intellispaces.framework.javastatements.context;

import tech.intellispaces.framework.javastatements.statement.reference.NamedTypeReference;
import tech.intellispaces.framework.javastatements.statement.reference.NonPrimitiveTypeReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeContextBuilder {
  private TypeContext parentContext = EMPTY_NAME_CONTEXT;
  private final Map<String, ContextTypeParameter> map = new HashMap<>();

  private static final TypeContextEmpty EMPTY_NAME_CONTEXT = new TypeContextEmpty();

  public static TypeContext empty() {
    return EMPTY_NAME_CONTEXT;
  }

  public static TypeContextBlank blank() {
    return new TypeContextImpl();
  }

  public static TypeContextBuilder get() {
    return new TypeContextBuilder();
  }

  public TypeContextBuilder parentContext(TypeContext parentContext) {
    this.parentContext = parentContext;
    return this;
  }

  public TypeContextBuilder addTypeParam(String typeParamName, NamedTypeReference type) {
    this.map.put(typeParamName, new ContextTypeParameterImpl(type, null));
    return this;
  }

  public TypeContextBuilder addTypeParams(List<NamedTypeReference> typeParams) {
    typeParams.forEach(typeParam ->
        this.map.put(typeParam.name(), new ContextTypeParameterImpl(typeParam, null))
    );
    return this;
  }

  public TypeContextBuilder addTypeParam(String typeParamName, NamedTypeReference type, NonPrimitiveTypeReference actualType) {
    this.map.put(typeParamName, new ContextTypeParameterImpl(type, actualType));
    return this;
  }

  public TypeContext build() {
    return new TypeContextImpl(parentContext, map);
  }

  private TypeContextBuilder() {}
}
