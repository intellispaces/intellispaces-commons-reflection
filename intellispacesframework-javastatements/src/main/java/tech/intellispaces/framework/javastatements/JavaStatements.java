package tech.intellispaces.framework.javastatements;

import tech.intellispaces.framework.commons.exception.UnexpectedViolationException;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.session.Sessions;
import tech.intellispaces.framework.javastatements.statement.Statement;
import tech.intellispaces.framework.javastatements.statement.TypeElementFunctions;
import tech.intellispaces.framework.javastatements.statement.custom.AnnotationStatement;
import tech.intellispaces.framework.javastatements.statement.custom.AnnotationStatements;
import tech.intellispaces.framework.javastatements.statement.custom.ClassStatement;
import tech.intellispaces.framework.javastatements.statement.custom.ClassStatements;
import tech.intellispaces.framework.javastatements.statement.custom.CustomType;
import tech.intellispaces.framework.javastatements.statement.reference.CustomTypeReferences;
import tech.intellispaces.framework.javastatements.statement.custom.CustomTypes;
import tech.intellispaces.framework.javastatements.statement.custom.EnumStatement;
import tech.intellispaces.framework.javastatements.statement.custom.EnumStatements;
import tech.intellispaces.framework.javastatements.statement.custom.InterfaceStatement;
import tech.intellispaces.framework.javastatements.statement.custom.InterfaceStatements;
import tech.intellispaces.framework.javastatements.statement.custom.RecordStatement;
import tech.intellispaces.framework.javastatements.statement.custom.RecordStatements;
import tech.intellispaces.framework.javastatements.statement.method.MethodFunctions;
import tech.intellispaces.framework.javastatements.statement.reference.CustomTypeReference;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

/**
 * Java statements provider.
 */
public interface JavaStatements {

  static Statement statement(Element element) {
    if (element instanceof TypeElement typeElement) {
      return TypeElementFunctions.asCustomTypeStatement(typeElement, Sessions.create());
    } else if (element instanceof ExecutableElement executableElement) {
      return MethodFunctions.getMethod(executableElement, Sessions.create());
    } else {
      throw UnexpectedViolationException.withMessage("Not supported element kind - {}", element.getKind());
    }
  }

  static CustomType customTypeStatement(Class<?> aClass) {
    return CustomTypes.of(aClass);
  }

  static CustomType customTypeStatement(TypeElement typeElement) {
    return TypeElementFunctions.asCustomTypeStatement(typeElement, Sessions.create());
  }

  static CustomType customTypeStatement(TypeElement typeElement, Session session) {
    return TypeElementFunctions.asCustomTypeStatement(typeElement, session);
  }

  static ClassStatement classStatement(TypeElement typeElement) {
    return ClassStatements.of(typeElement, Sessions.create());
  }

  static ClassStatement classStatement(TypeElement typeElement, Session session) {
    return ClassStatements.of(typeElement, session);
  }

  static InterfaceStatement interfaceStatement(TypeElement typeElement) {
    return InterfaceStatements.of(typeElement, Sessions.create());
  }

  static InterfaceStatement interfaceStatement(TypeElement typeElement, Session session) {
    return InterfaceStatements.of(typeElement, session);
  }

  static RecordStatement recordStatement(TypeElement typeElement) {
    return RecordStatements.of(typeElement, Sessions.create());
  }

  static RecordStatement recordStatement(TypeElement typeElement, Session session) {
    return RecordStatements.of(typeElement, session);
  }

  static EnumStatement enumStatement(TypeElement typeElement) {
    return EnumStatements.of(typeElement, Sessions.create());
  }

  static EnumStatement enumStatement(TypeElement typeElement, Session session) {
    return EnumStatements.of(typeElement, session);
  }

  static AnnotationStatement annotationStatement(TypeElement typeElement) {
    return AnnotationStatements.of(typeElement, Sessions.create());
  }

  static AnnotationStatement annotationStatement(TypeElement typeElement, Session session) {
    return AnnotationStatements.of(typeElement, session);
  }

  static CustomTypeReference customTypeReference(Class<?> aClass) {
    return CustomTypeReferences.of(aClass);
  }

  static CustomTypeReference customTypeReference(TypeElement typeElement) {
    return CustomTypeReferences.of(typeElement, Sessions.create());
  }

  static CustomTypeReference customTypeReference(TypeElement typeElement, Session session) {
    return CustomTypeReferences.of(typeElement, session);
  }

  static CustomTypeReference customTypeReference(DeclaredType declaredType) {
    return CustomTypeReferences.of(declaredType, Sessions.create());
  }

  static CustomTypeReference customTypeReference(DeclaredType declaredType, Session session) {
    return CustomTypeReferences.of(declaredType, session);
  }
}
