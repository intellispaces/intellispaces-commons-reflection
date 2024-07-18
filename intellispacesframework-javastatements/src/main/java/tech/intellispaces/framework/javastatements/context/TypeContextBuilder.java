package tech.intellispaces.framework.javastatements.context;

import tech.intellispaces.framework.javastatements.statement.reference.NamedTypeReference;
import tech.intellispaces.framework.javastatements.statement.reference.NonPrimitiveTypeReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeContextBuilder {
  private TypeContext parentContext = TypeContexts.empty();
  private final Map<String, ContextTypeParameter> map = new HashMap<>();

  TypeContextBuilder() {}

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

  public TypeContextBuilder addTypeParam(
      String typeParamName, NamedTypeReference type, NonPrimitiveTypeReference actualType
  ) {
    this.map.put(typeParamName, new ContextTypeParameterImpl(type, actualType));
    return this;
  }

  public TypeContext get() {
    return new TypeContextImpl(parentContext, map);
  }
}
