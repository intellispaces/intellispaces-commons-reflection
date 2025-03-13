package tech.intellispaces.commons.reflection.context;

import tech.intellispaces.commons.reflection.reference.NamedReference;
import tech.intellispaces.commons.reflection.reference.NotPrimitiveReference;

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

  public TypeContextBuilder addTypeParam(String typeParamName, NamedReference type) {
    this.map.put(typeParamName, new ContextTypeParameterImpl(type, null));
    return this;
  }

  public TypeContextBuilder addTypeParams(List<NamedReference> typeParams) {
    typeParams.forEach(typeParam ->
        this.map.put(typeParam.name(), new ContextTypeParameterImpl(typeParam, null))
    );
    return this;
  }

  public TypeContextBuilder addTypeParam(
      String typeParamName, NamedReference type, NotPrimitiveReference actualType
  ) {
    this.map.put(typeParamName, new ContextTypeParameterImpl(type, actualType));
    return this;
  }

  public TypeContext get() {
    return new TypeContextImpl(parentContext, map);
  }
}
