package tech.intellispaces.reflection.reference;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import tech.intellispaces.commons.collection.ArraysFunctions;
import tech.intellispaces.commons.exception.UnexpectedExceptions;
import tech.intellispaces.reflection.customtype.CustomType;

public interface TypeReferences {

  static TypeReference of(java.lang.reflect.Type type) {
    if (type instanceof Class<?>) {
      return CustomTypeReferences.get((Class<?>) type);
    } else if (type instanceof ParameterizedType pt) {
      CustomType baseType = TypeReferences.of(pt.getRawType()).asCustomTypeReferenceOrElseThrow().targetType();
      List<NotPrimitiveReference> qualifiers = new ArrayList<>();
      ArraysFunctions.foreach(pt.getActualTypeArguments(), t -> {
        qualifiers.add((NotPrimitiveReference) TypeReferences.of(t));
      });
      return CustomTypeReferences.get(baseType, qualifiers);
    }
    throw UnexpectedExceptions.withMessage("Unsupported type");
  }

  static TypeReference of(Class<?> aClass) {
    if (aClass.isPrimitive()) {
      return PrimitiveReferences.get(aClass.getTypeName());
    } else if (aClass.isArray()) {
      return ArrayTypeReferences.of(aClass.componentType());
    } else {
      return CustomTypeReferences.get(aClass);
    }
  }
}
