package intellispaces.javastatements;

import intellispaces.javastatements.function.ElementFunctions;
import intellispaces.javastatements.model.custom.AnnotationStatement;
import intellispaces.javastatements.model.custom.ClassStatement;
import intellispaces.javastatements.model.custom.CustomType;
import intellispaces.javastatements.model.custom.EnumStatement;
import intellispaces.javastatements.model.custom.InterfaceStatement;
import intellispaces.javastatements.model.custom.RecordStatement;
import intellispaces.javastatements.model.reference.CustomTypeReference;
import intellispaces.javastatements.model.session.Session;
import intellispaces.javastatements.object.session.SessionFactory;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

/**
 * Java statements facade functions.
 */
public interface JavaStatements {

  static CustomType customTypeStatement(TypeElement typeElement) {
    return ElementFunctions.asCustomTypeStatement(typeElement, SessionFactory.create());
  }

  static CustomType customTypeStatement(TypeElement typeElement, Session session) {
    return ElementFunctions.asCustomTypeStatement(typeElement, session);
  }

  static ClassStatement classStatement(TypeElement typeElement) {
    return ElementFunctions.asClassStatement(typeElement, SessionFactory.create());
  }

  static ClassStatement classStatement(TypeElement typeElement, Session session) {
    return ElementFunctions.asClassStatement(typeElement, session);
  }

  static InterfaceStatement interfaceStatement(TypeElement typeElement) {
    return ElementFunctions.asInterfaceStatement(typeElement, SessionFactory.create());
  }

  static InterfaceStatement interfaceStatement(TypeElement typeElement, Session session) {
    return ElementFunctions.asInterfaceStatement(typeElement, session);
  }

  static RecordStatement recordStatement(TypeElement typeElement) {
    return ElementFunctions.asRecordStatement(typeElement, SessionFactory.create());
  }

  static RecordStatement recordStatement(TypeElement typeElement, Session session) {
    return ElementFunctions.asRecordStatement(typeElement, session);
  }

  static EnumStatement enumStatement(TypeElement typeElement) {
    return ElementFunctions.asEnumStatement(typeElement, SessionFactory.create());
  }

  static EnumStatement enumStatement(TypeElement typeElement, Session session) {
    return ElementFunctions.asEnumStatement(typeElement, session);
  }

  static AnnotationStatement annotationStatement(TypeElement typeElement) {
    return ElementFunctions.asAnnotationStatement(typeElement, SessionFactory.create());
  }

  static AnnotationStatement annotationStatement(TypeElement typeElement, Session session) {
    return ElementFunctions.asAnnotationStatement(typeElement, session);
  }

  static CustomTypeReference customTypeReference(TypeElement typeElement) {
    return ElementFunctions.asTypeReference(typeElement, SessionFactory.create());
  }

  static CustomTypeReference customTypeReference(TypeElement typeElement, Session session) {
    return ElementFunctions.asTypeReference(typeElement, session);
  }

  static CustomTypeReference customTypeReference(DeclaredType declaredType) {
    return ElementFunctions.asTypeReference(declaredType, SessionFactory.create());
  }

  static CustomTypeReference customTypeReference(DeclaredType declaredType, Session session) {
    return ElementFunctions.asTypeReference(declaredType, session);
  }
}
