package tech.intellispaces.javastatements.reference;

import tech.intellispaces.commons.collection.ArraysFunctions;
import tech.intellispaces.commons.exception.UnexpectedViolationException;
import tech.intellispaces.javastatements.customtype.CustomType;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public interface TypeReferences {

  static TypeReference of(java.lang.reflect.Type type) {
    if (type instanceof Class<?>) {
      return CustomTypeReferences.of((Class<?>) type);
    } else if (type instanceof ParameterizedType pt) {
      CustomType baseType = TypeReferences.of(pt.getRawType()).asCustomTypeReferenceOrElseThrow().targetType();
      List<NotPrimitiveReference> qualifiers = new ArrayList<>();
      ArraysFunctions.foreach(pt.getActualTypeArguments(), t -> {
        qualifiers.add((NotPrimitiveReference) TypeReferences.of(t));
      });
      return CustomTypeReferences.of(baseType, qualifiers);
    }
    throw UnexpectedViolationException.withMessage("Unsupported type");
  }
}
