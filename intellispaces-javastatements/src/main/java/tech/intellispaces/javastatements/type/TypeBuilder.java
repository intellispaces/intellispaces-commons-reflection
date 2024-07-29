package tech.intellispaces.javastatements.type;

import tech.intellispaces.javastatements.statement.reference.TypeReferences;
import tech.intellispaces.javastatements.statement.reference.TypeReference;

import java.lang.reflect.ParameterizedType;

public abstract class TypeBuilder<T> {
  private final Type<T> type;

  protected TypeBuilder() {
    java.lang.reflect.Type reflectType = getClass().getGenericSuperclass();
    ParameterizedType parameterizedType = (ParameterizedType) reflectType;
    java.lang.reflect.Type[] typeArguments = parameterizedType.getActualTypeArguments();
    TypeReference typeReference = TypeReferences.of(typeArguments[0]);
    this.type = TypeFunctions.get(typeReference);
  }

  public Type<T> get() {
    return type;
  }
}
