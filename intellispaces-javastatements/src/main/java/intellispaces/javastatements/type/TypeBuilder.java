package intellispaces.javastatements.type;

import intellispaces.javastatements.reference.TypeReference;
import intellispaces.javastatements.reference.TypeReferences;

import java.lang.reflect.ParameterizedType;

public abstract class TypeBuilder<T> {
  private final Type<T> type;

  protected TypeBuilder() {
    java.lang.reflect.Type reflectType = getClass().getGenericSuperclass();
    ParameterizedType parameterizedType = (ParameterizedType) reflectType;
    java.lang.reflect.Type[] typeArguments = parameterizedType.getActualTypeArguments();
    TypeReference typeReference = TypeReferences.of(typeArguments[0]);
    this.type = Types.get(typeReference);
  }

  public Type<T> get() {
    return type;
  }
}
