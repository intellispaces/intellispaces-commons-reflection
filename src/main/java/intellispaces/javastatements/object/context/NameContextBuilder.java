package intellispaces.javastatements.object.context;

import intellispaces.javastatements.model.context.ContextTypeParameter;
import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.reference.NamedTypeReference;
import intellispaces.javastatements.model.reference.NonPrimitiveTypeReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class NameContextBuilder {
  private NameContext parentContext = EMPTY_NAME_CONTEXT;
  private Map<String, ContextTypeParameter> map = new HashMap<>();

  private static final EmptyNameContext EMPTY_NAME_CONTEXT = new EmptyNameContext();

  public static NameContext empty() {
    return EMPTY_NAME_CONTEXT;
  }

  public static NameContextBlank blank() {
    return new NameContextObject();
  }

  public static NameContextBuilder get() {
    return new NameContextBuilder();
  }

  public NameContextBuilder parentContext(NameContext parentContext) {
    this.parentContext = parentContext;
    return this;
  }

  public NameContextBuilder addTypeParam(String typeParamName, NamedTypeReference type) {
    this.map.put(typeParamName, new ContextTypeParameterObject(type, null));
    return this;
  }

  public NameContextBuilder addTypeParams(List<NamedTypeReference> typeParams) {
    typeParams.forEach(typeParam ->
        this.map.put(typeParam.name(), new ContextTypeParameterObject(typeParam, null))
    );
    return this;
  }

  public NameContextBuilder addTypeParam(String typeParamName, NamedTypeReference type, NonPrimitiveTypeReference actualType) {
    this.map.put(typeParamName, new ContextTypeParameterObject(type, actualType));
    return this;
  }

  public NameContext build() {
    return new NameContextObject(parentContext, map);
  }

  private NameContextBuilder() {}
}
