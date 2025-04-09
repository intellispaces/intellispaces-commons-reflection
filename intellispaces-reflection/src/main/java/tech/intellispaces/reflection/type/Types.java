package tech.intellispaces.reflection.type;

import tech.intellispaces.reflection.reference.CustomTypeReferences;
import tech.intellispaces.reflection.reference.TypeReference;

import java.util.List;

public interface Types {

  static <T> Type<T> get(TypeReference typeReference) {
    return new TypeBaseOnReferenceImpl<>(typeReference);
  }

  static <T extends B, B> Type<T> get(Class<B> baseClass) {
    return new ReferenceBasedType<>(CustomTypeReferences.get(baseClass), List.of());
  }

  static <T extends B, B, Q> Type<T> get(
      Class<B> baseClass, Class<Q> qualifierClass
  ) {
    return new ReferenceBasedType<>(
        CustomTypeReferences.get(baseClass), List.of(
            CustomTypeReferences.get(qualifierClass)
        )
    );
  }

  static <T extends B, B, Q1, Q2> Type<T> get(
      Class<B> baseClass, Class<Q1> qualifierClass1, Class<Q2> qualifierClass2
  ) {
    return new ReferenceBasedType<>(
        CustomTypeReferences.get(baseClass), List.of(
            CustomTypeReferences.get(qualifierClass1),
            CustomTypeReferences.get(qualifierClass2)
      )
    );
  }

  static <T extends B, B, Q1, Q2, Q3> Type<T> get(
      Class<B> baseClass,
      Class<Q1> qualifierClass1,
      Class<Q2> qualifierClass2,
      Class<Q3> qualifierClass3
  ) {
    return new ReferenceBasedType<>(
        CustomTypeReferences.get(baseClass), List.of(
            CustomTypeReferences.get(qualifierClass1),
            CustomTypeReferences.get(qualifierClass2),
            CustomTypeReferences.get(qualifierClass3)
      )
    );
  }

  static <T extends B, B, Q> Type<T> get(
      Class<B> baseClass, Type<Q> qualifierType
  ) {
    return new ReferenceBasedType<>(
        CustomTypeReferences.get(baseClass), List.of(qualifierType.typeReference())
    );
  }

  static <T extends B, B, Q1, Q2> Type<T> get(
      Class<B> baseClass,
      Type<Q1> qualifierType1,
      Type<Q2> qualifierType2
  ) {
    return new ReferenceBasedType<>(
        CustomTypeReferences.get(baseClass), List.of(
        qualifierType1.typeReference(),
        qualifierType2.typeReference()
      )
    );
  }

  static <T extends B, B, Q1, Q2, Q3> Type<T> get(
      Class<B> baseClass,
      Type<Q1> qualifierType1,
      Type<Q2> qualifierType2,
      Type<Q3> qualifierType3
  ) {
    return new ReferenceBasedType<>(
        CustomTypeReferences.get(baseClass), List.of(
        qualifierType1.typeReference(),
        qualifierType2.typeReference(),
        qualifierType3.typeReference()
      )
    );
  }
}
