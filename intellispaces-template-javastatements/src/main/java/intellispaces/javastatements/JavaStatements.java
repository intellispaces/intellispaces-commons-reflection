package intellispaces.javastatements;

import intellispaces.javastatements.session.SessionBuilder;
import intellispaces.javastatements.statement.TypeElementFunctions;
import intellispaces.javastatements.statement.custom.AnnotationStatement;
import intellispaces.javastatements.statement.custom.ClassStatement;
import intellispaces.javastatements.statement.custom.CustomType;
import intellispaces.javastatements.statement.custom.EnumStatement;
import intellispaces.javastatements.statement.custom.InterfaceStatement;
import intellispaces.javastatements.statement.custom.RecordStatement;
import intellispaces.javastatements.statement.reference.CustomTypeReference;
import intellispaces.javastatements.session.Session;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

/**
 * Java statements facade functions.
 */
public interface JavaStatements {

  static CustomType customTypeStatement(TypeElement typeElement) {
    return TypeElementFunctions.asCustomTypeStatement(typeElement, SessionBuilder.buildSession());
  }

  static CustomType customTypeStatement(TypeElement typeElement, Session session) {
    return TypeElementFunctions.asCustomTypeStatement(typeElement, session);
  }

  static ClassStatement classStatement(TypeElement typeElement) {
    return TypeElementFunctions.asClassStatement(typeElement, SessionBuilder.buildSession());
  }

  static ClassStatement classStatement(TypeElement typeElement, Session session) {
    return TypeElementFunctions.asClassStatement(typeElement, session);
  }

  static InterfaceStatement interfaceStatement(TypeElement typeElement) {
    return TypeElementFunctions.asInterfaceStatement(typeElement, SessionBuilder.buildSession());
  }

  static InterfaceStatement interfaceStatement(TypeElement typeElement, Session session) {
    return TypeElementFunctions.asInterfaceStatement(typeElement, session);
  }

  static RecordStatement recordStatement(TypeElement typeElement) {
    return TypeElementFunctions.asRecordStatement(typeElement, SessionBuilder.buildSession());
  }

  static RecordStatement recordStatement(TypeElement typeElement, Session session) {
    return TypeElementFunctions.asRecordStatement(typeElement, session);
  }

  static EnumStatement enumStatement(TypeElement typeElement) {
    return TypeElementFunctions.asEnumStatement(typeElement, SessionBuilder.buildSession());
  }

  static EnumStatement enumStatement(TypeElement typeElement, Session session) {
    return TypeElementFunctions.asEnumStatement(typeElement, session);
  }

  static AnnotationStatement annotationStatement(TypeElement typeElement) {
    return TypeElementFunctions.asAnnotationStatement(typeElement, SessionBuilder.buildSession());
  }

  static AnnotationStatement annotationStatement(TypeElement typeElement, Session session) {
    return TypeElementFunctions.asAnnotationStatement(typeElement, session);
  }

  static CustomTypeReference customTypeReference(TypeElement typeElement) {
    return TypeElementFunctions.asTypeReference(typeElement, SessionBuilder.buildSession());
  }

  static CustomTypeReference customTypeReference(TypeElement typeElement, Session session) {
    return TypeElementFunctions.asTypeReference(typeElement, session);
  }

  static CustomTypeReference customTypeReference(DeclaredType declaredType) {
    return TypeElementFunctions.asTypeReference(declaredType, SessionBuilder.buildSession());
  }

  static CustomTypeReference customTypeReference(DeclaredType declaredType, Session session) {
    return TypeElementFunctions.asTypeReference(declaredType, session);
  }
}
