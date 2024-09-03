package intellispaces.common.javastatements.reference;

import intellispaces.common.javastatements.context.TypeContext;
import intellispaces.common.javastatements.context.TypeContexts;
import intellispaces.common.javastatements.customtype.CustomType;
import intellispaces.common.javastatements.session.Session;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import java.util.List;

public interface CustomTypeReferences {

  static CustomTypeReference get(CustomType targetType) {
    return get(targetType, List.of());
  }

  static CustomTypeReference get(CustomType customType, List<NotPrimitiveReference> typeArguments) {
    return new CustomTypeReferenceImpl(customType, typeArguments);
  }

  static CustomTypeReference get(Class<?> aClass) {
    return new CustomTypeReferenceBasedOnClass(aClass);
  }

  static CustomTypeReference get(TypeElement typeElement, Session session) {
    return get(typeElement, TypeContexts.empty(), session);
  }

  static CustomTypeReference get(TypeElement typeElement, TypeContext typeContext, Session session) {
    return new CustomTypeReferenceBasedOnTypeElement(typeElement, typeContext, session);
  }

  static CustomTypeReference get(DeclaredType declaredType, Session session) {
    return get(declaredType, TypeContexts.empty(), session);
  }

  static CustomTypeReference get(DeclaredType declaredType, TypeContext typeContext, Session session) {
    return new CustomTypeReferenceBasedOnDeclaredType(declaredType, typeContext, session);
  }
}
