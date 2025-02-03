package tech.intellispaces.commons.java.reflection.type;

import tech.intellispaces.commons.java.reflection.reference.CustomTypeReferences;
import tech.intellispaces.commons.java.reflection.reference.TypeReference;

import java.util.List;

public interface DerivedTypes {

  static <T> DerivedType<T> get(TypeReference typeReference) {
    return new DerivedTypeBaseOnReferenceImpl<>(typeReference);
  }

  static <T extends B, B> DerivedType<T> get(Class<B> baseClass) {
    return new ReferenceBasedDerivedType<>(CustomTypeReferences.get(baseClass), List.of());
  }

  static <T extends B, B, Q> DerivedType<T> get(
      Class<B> baseClass, Class<Q> qualifierClass
  ) {
    return new ReferenceBasedDerivedType<>(
        CustomTypeReferences.get(baseClass), List.of(
            CustomTypeReferences.get(qualifierClass)
        )
    );
  }

  static <T extends B, B, Q1, Q2> DerivedType<T> get(
      Class<B> baseClass, Class<Q1> qualifierClass1, Class<Q2> qualifierClass2
  ) {
    return new ReferenceBasedDerivedType<>(
        CustomTypeReferences.get(baseClass), List.of(
            CustomTypeReferences.get(qualifierClass1),
            CustomTypeReferences.get(qualifierClass2)
      )
    );
  }

  static <T extends B, B, Q1, Q2, Q3> DerivedType<T> get(
      Class<B> baseClass,
      Class<Q1> qualifierClass1,
      Class<Q2> qualifierClass2,
      Class<Q3> qualifierClass3
  ) {
    return new ReferenceBasedDerivedType<>(
        CustomTypeReferences.get(baseClass), List.of(
            CustomTypeReferences.get(qualifierClass1),
            CustomTypeReferences.get(qualifierClass2),
            CustomTypeReferences.get(qualifierClass3)
      )
    );
  }

  static <T extends B, B, Q> DerivedType<T> get(
      Class<B> baseClass, DerivedType<Q> qualifierType
  ) {
    return new ReferenceBasedDerivedType<>(
        CustomTypeReferences.get(baseClass), List.of(qualifierType.typeReference())
    );
  }

  static <T extends B, B, Q1, Q2> DerivedType<T> get(
      Class<B> baseClass,
      DerivedType<Q1> qualifierType1,
      DerivedType<Q2> qualifierType2
  ) {
    return new ReferenceBasedDerivedType<>(
        CustomTypeReferences.get(baseClass), List.of(
        qualifierType1.typeReference(),
        qualifierType2.typeReference()
      )
    );
  }

  static <T extends B, B, Q1, Q2, Q3> DerivedType<T> get(
      Class<B> baseClass,
      DerivedType<Q1> qualifierType1,
      DerivedType<Q2> qualifierType2,
      DerivedType<Q3> qualifierType3
  ) {
    return new ReferenceBasedDerivedType<>(
        CustomTypeReferences.get(baseClass), List.of(
        qualifierType1.typeReference(),
        qualifierType2.typeReference(),
        qualifierType3.typeReference()
      )
    );
  }
}
