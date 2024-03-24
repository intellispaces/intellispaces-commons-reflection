package intellispaces.javastatements.object.reference;

import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.custom.CustomType;
import intellispaces.javastatements.model.reference.CustomTypeReference;
import intellispaces.javastatements.model.reference.NonPrimitiveTypeReference;
import intellispaces.javastatements.model.session.Session;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import java.util.List;

public interface CustomTypeReferenceBuilder {

  static CustomTypeReference build(CustomType targetType) {
    return build(targetType, List.of());
  }

  static CustomTypeReference build(CustomType targetType, List<NonPrimitiveTypeReference> typeArguments) {
    return new CustomTypeReferenceObject(targetType, typeArguments);
  }

  static CustomTypeReference build(DeclaredType declaredType, NameContext nameContext, Session session) {
    return new CustomTypeReferenceFromDeclaredTypeAdapter(declaredType, nameContext, session);
  }

  static CustomTypeReference build(TypeElement typeElement, NameContext nameContext, Session session) {
    return new CustomTypeReferenceFromTypeElementAdapter(typeElement, nameContext, session);
  }
}
