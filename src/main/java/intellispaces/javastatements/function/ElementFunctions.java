package intellispaces.javastatements.function;

import intellispaces.commons.model.function.TriFunction;
import intellispaces.javastatements.exception.JavaStatementException;
import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.custom.AnnotationStatement;
import intellispaces.javastatements.model.custom.ClassStatement;
import intellispaces.javastatements.model.custom.CustomType;
import intellispaces.javastatements.model.custom.EnumStatement;
import intellispaces.javastatements.model.custom.InterfaceStatement;
import intellispaces.javastatements.model.custom.MethodSignature;
import intellispaces.javastatements.model.custom.RecordStatement;
import intellispaces.javastatements.model.reference.ArrayTypeReference;
import intellispaces.javastatements.model.reference.CustomTypeReference;
import intellispaces.javastatements.model.session.Session;
import intellispaces.javastatements.object.custom.AnnotationStatementBuilder;
import intellispaces.javastatements.object.custom.ClassStatementBuilder;
import intellispaces.javastatements.object.reference.ArrayTypeReferenceBuilder;
import intellispaces.javastatements.object.custom.EnumStatementBuilder;
import intellispaces.javastatements.object.custom.InterfaceStatementBuilder;
import intellispaces.javastatements.object.custom.MethodSignatureBuilder;
import intellispaces.javastatements.object.context.NameContextBuilder;
import intellispaces.javastatements.object.custom.RecordStatementBuilder;
import intellispaces.javastatements.object.reference.CustomTypeReferenceBuilder;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;

public interface ElementFunctions {

  static CustomType asCustomTypeStatement(TypeElement typeElement, Session session) {
    return asCustomTypeStatement(typeElement, NameContextBuilder.empty(), session);
  }

  static CustomType asCustomTypeStatement(TypeElement typeElement, NameContext nameContext, Session session) {
    if (typeElement.getKind() == ElementKind.CLASS) {
      return asClassStatement(typeElement, nameContext, session);
    } else if (typeElement.getKind() == ElementKind.INTERFACE) {
      return asInterfaceStatement(typeElement, nameContext, session);
    } else if (typeElement.getKind() == ElementKind.RECORD) {
      return asRecordStatement(typeElement, nameContext, session);
    } else if (typeElement.getKind() == ElementKind.ENUM) {
      return asEnumStatement(typeElement, nameContext, session);
    } else if (typeElement.getKind() == ElementKind.ANNOTATION_TYPE) {
      return asAnnotationStatement(typeElement, nameContext, session);
    } else {
      throw new JavaStatementException("Type element of kind can't be represented as custom type statement" + typeElement.getKind());
    }
  }

  static ClassStatement asClassStatement(TypeElement typeElement, Session session) {
    return asClassStatement(typeElement, NameContextBuilder.empty(), session);
  }

  static ClassStatement asClassStatement(TypeElement typeElement, NameContext nameContext, Session session) {
    return asCustomTypeStatement(typeElement, ElementKind.CLASS, ClassStatementBuilder::build, nameContext, session);
  }

  static InterfaceStatement asInterfaceStatement(TypeElement typeElement, Session session) {
    return asInterfaceStatement(typeElement, NameContextBuilder.empty(), session);
  }

  static InterfaceStatement asInterfaceStatement(TypeElement typeElement, NameContext nameContext, Session session) {
    return asCustomTypeStatement(typeElement, ElementKind.INTERFACE, InterfaceStatementBuilder::build, nameContext, session);
  }

  static RecordStatement asRecordStatement(TypeElement typeElement, Session session) {
    return asRecordStatement(typeElement, NameContextBuilder.empty(), session);
  }

  static RecordStatement asRecordStatement(TypeElement typeElement, NameContext nameContext, Session session) {
    return asCustomTypeStatement(typeElement, ElementKind.RECORD, RecordStatementBuilder::build, nameContext, session);
  }

  static EnumStatement asEnumStatement(TypeElement typeElement, Session session) {
    return asEnumStatement(typeElement, NameContextBuilder.empty(), session);
  }

  static EnumStatement asEnumStatement(TypeElement typeElement, NameContext nameContext, Session session) {
    return asCustomTypeStatement(typeElement, ElementKind.ENUM, EnumStatementBuilder::build, nameContext, session);
  }

  static AnnotationStatement asAnnotationStatement(TypeElement typeElement, Session session) {
    return asAnnotationStatement(typeElement, NameContextBuilder.empty(), session);
  }

  static AnnotationStatement asAnnotationStatement(TypeElement typeElement, NameContext nameContext, Session session) {
    return asCustomTypeStatement(typeElement, ElementKind.ANNOTATION_TYPE, AnnotationStatementBuilder::build, nameContext, session);
  }

  @SuppressWarnings("unchecked")
  private static <T extends CustomType> T asCustomTypeStatement(
      TypeElement typeElement,
      ElementKind expectedElementKind,
      TriFunction<TypeElement, NameContext, Session, T> builder,
      NameContext nameContext,
      Session session
  ) {
    if (typeElement.getKind() != expectedElementKind) {
      throw new JavaStatementException("Expected type element of the kind {} but actual is {}", expectedElementKind, typeElement.getKind());
    }

    String typeName = TypeFunctions.getCanonicalName(typeElement);
    T statement = (T) session.getType(typeName);
    if (statement == null) {
      statement = builder.apply(typeElement, nameContext, session);
      session.putType(typeName, statement);
    }
    return statement;
  }

  static CustomTypeReference asTypeReference(TypeElement typeElement, Session session) {
    return asTypeReference(typeElement, NameContextBuilder.empty(), session);
  }

  static CustomTypeReference asTypeReference(TypeElement typeElement, NameContext nameContext, Session session) {
    return CustomTypeReferenceBuilder.build(typeElement, nameContext, session);
  }

  static CustomTypeReference asTypeReference(DeclaredType declaredType, Session session) {
    return asTypeReference(declaredType, NameContextBuilder.empty(), session);
  }

  static CustomTypeReference asTypeReference(DeclaredType declaredType, NameContext nameContext, Session session) {
    return CustomTypeReferenceBuilder.build(declaredType, nameContext, session);
  }

  static ArrayTypeReference asTypeReference(ArrayType arrayType, Session session) {
    return asTypeReference(arrayType, NameContextBuilder.empty(), session);
  }

  static ArrayTypeReference asTypeReference(ArrayType arrayType, NameContext nameContext, Session session) {
    return ArrayTypeReferenceBuilder.build(arrayType, nameContext, session);
  }

  static MethodSignature asMethodSignature(ExecutableElement executableElement, NameContext nameContext, Session session) {
    return MethodSignatureBuilder.build(executableElement, nameContext, session);
  }
}
