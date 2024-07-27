package tech.intellispaces.framework.javastatements.statement.common;

import tech.intellispaces.framework.commons.function.TriFunction;
import tech.intellispaces.framework.javastatements.context.ContextTypeParameter;
import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.context.TypeContextBlank;
import tech.intellispaces.framework.javastatements.context.TypeContexts;
import tech.intellispaces.framework.javastatements.exception.JavaStatementException;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.custom.AnnotationFunctions;
import tech.intellispaces.framework.javastatements.statement.custom.AnnotationStatements;
import tech.intellispaces.framework.javastatements.statement.custom.ClassStatements;
import tech.intellispaces.framework.javastatements.statement.custom.CustomStatement;
import tech.intellispaces.framework.javastatements.statement.custom.EnumStatements;
import tech.intellispaces.framework.javastatements.statement.custom.InterfaceStatements;
import tech.intellispaces.framework.javastatements.statement.custom.RecordStatements;
import tech.intellispaces.framework.javastatements.statement.instance.AnnotationInstance;
import tech.intellispaces.framework.javastatements.statement.method.MethodStatement;
import tech.intellispaces.framework.javastatements.statement.method.MethodStatements;
import tech.intellispaces.framework.javastatements.statement.type.ArrayTypeReferences;
import tech.intellispaces.framework.javastatements.statement.type.CustomType;
import tech.intellispaces.framework.javastatements.statement.type.CustomTypeReferences;
import tech.intellispaces.framework.javastatements.statement.type.NamedType;
import tech.intellispaces.framework.javastatements.statement.type.NamedTypeReferences;
import tech.intellispaces.framework.javastatements.statement.type.NotPrimitiveType;
import tech.intellispaces.framework.javastatements.statement.type.PrimitiveTypeReferences;
import tech.intellispaces.framework.javastatements.statement.type.Type;
import tech.intellispaces.framework.javastatements.statement.type.TypeBound;
import tech.intellispaces.framework.javastatements.statement.type.WildcardTypeReferences;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Parameterizable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.WildcardType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Functions related to javax.lang.model elements and types.
 */
public interface TypeElementFunctions {

  static String getCanonicalName(TypeElement typeElement) {
    return typeElement.getQualifiedName().toString();
  }

  static String getClassName(TypeElement typeElement) {
    if (typeElement.getEnclosingElement().getKind().equals(ElementKind.PACKAGE)) {
      return typeElement.getQualifiedName().toString();
    }

    List<String> typenames = new ArrayList<>();
    Element curElement = typeElement;
    while (true) {
      Element enclosingElement = curElement.getEnclosingElement();
      if (!enclosingElement.getKind().isClass() && !enclosingElement.getKind().isInterface()) {
        break;
      }
      typenames.add(curElement.getSimpleName().toString());
      curElement = curElement.getEnclosingElement();
    }

    var sb = new StringBuilder();
    sb.append(((TypeElement) curElement).getQualifiedName().toString());
    for (int i = typenames.size() - 1; i >= 0; i--) {
      sb.append("$");
      sb.append(typenames.get(i));
    }
    return sb.toString();
  }

  static String getSimpleName(TypeElement typeElement) {
    return typeElement.getSimpleName().toString();
  }

  static String getPackageName(TypeElement typeElement) {
    TypeElement element = typeElement;
    while (element.getEnclosingElement().getKind() != ElementKind.PACKAGE) {
      element = (TypeElement) element.getEnclosingElement();
    }
    String canonicalName = getCanonicalName(element);
    int dot = canonicalName.lastIndexOf('.');
    return (dot != -1) ? canonicalName.substring(0, dot).intern() : "";
  }

  static List<CustomType> getParentTypes(TypeElement typeElement, Session session) {
    return getParentTypes(typeElement, TypeContexts.empty(), session);
  }

  static List<CustomType> getParentTypes(
      TypeElement typeElement, TypeContext typeContext, Session session
  ) {
    List<TypeMirror> parentTypeMirrors = new ArrayList<>(1 + typeElement.getInterfaces().size());
    TypeMirror superClass = typeElement.getSuperclass();
    if (superClass != null && TypeKind.NONE != superClass.getKind()) {
      parentTypeMirrors.add(superClass);
    }
    parentTypeMirrors.addAll(typeElement.getInterfaces());

    List<NamedType> thisTypeParams = getTypeParameters(typeElement, typeContext, session);
    TypeContext newTypeContext = TypeContexts.build()
        .parentContext(typeContext)
        .addTypeParams(thisTypeParams)
        .get();
    return parentTypeMirrors.stream()
        .map(parent -> CustomTypeReferences.of((DeclaredType) parent, newTypeContext, session))
        .filter(ref -> !Object.class.getName().equals(ref.statement().canonicalName()))
        .toList();
  }

  static List<NamedType> getTypeParameters(TypeElement typeElement, Session session) {
    return getTypeParameters(typeElement, TypeContexts.empty(), session);
  }

  static List<NamedType> getTypeParameters(
      Parameterizable parameterizable, TypeContext typeContext, Session session
  ) {
    TypeContextBlank nameContextBlank = TypeContexts.blank();
    nameContextBlank.setParentContext(typeContext);
    List<NamedType> typeParams = parameterizable.getTypeParameters().stream()
        .map(param -> getTypeParameter(param, nameContextBlank, session))
        .toList();
    typeParams.forEach(typeParam -> nameContextBlank.addTypeParam(typeParam.name(), typeParam));
    return typeParams;
  }

  private static NamedType getTypeParameter(
      TypeParameterElement typeParameter, TypeContext typeContext, Session session
  ) {
    return NamedTypeReferences.of(typeParameter, typeContext, session);
  }

  static List<NotPrimitiveType> getTypeArguments(
      DeclaredType declaredType, TypeContext typeContext, Session session
  ) {
    List<NotPrimitiveType> typeArguments = new ArrayList<>();
    for (TypeMirror typeArgumentsMirror : declaredType.getTypeArguments()) {
      Type typeArgumentsReference = getTypeReference(typeArgumentsMirror, typeContext, session);
      if (typeArgumentsReference instanceof NotPrimitiveType) {
        typeArguments.add((NotPrimitiveType) typeArgumentsReference);
      } else {
        throw JavaStatementException.withMessage("Invalid type kind");
      }
    }
    return typeArguments;
  }

  static List<AnnotationInstance> getAnnotations(Element typeElement, Session session) {
    return AnnotationFunctions.getAnnotations(typeElement.getAnnotationMirrors(), session);
  }

  static List<MethodStatement> getConstructors(
      TypeElement typeElement, CustomStatement methodOwner, TypeContext typeContext, Session session
  ) {
    return typeElement.getEnclosedElements().stream()
        .filter(element -> element.getKind() == ElementKind.CONSTRUCTOR)
        .map(element -> (ExecutableElement) element)
        .map(element -> MethodStatements.of(element, methodOwner, typeContext, session))
        .toList();
  }

  static List<MethodStatement> getDeclaredMethods(
      TypeElement typeElement, CustomStatement methodOwner, TypeContext typeContext, Session session
  ) {
    return typeElement.getEnclosedElements().stream()
        .filter(element -> element.getKind() == ElementKind.METHOD)
        .map(element -> (ExecutableElement) element)
        .map(element -> MethodStatements.of(element, methodOwner, typeContext, session))
        .toList();
  }

  static CustomType getTypeReference(DeclaredType declaredType, Session session) {
    return getTypeReference(declaredType, TypeContexts.empty(), session);
  }

  static CustomType getTypeReference(DeclaredType declaredType, TypeContext typeContext, Session session) {
    return CustomTypeReferences.of(declaredType, typeContext, session);
  }

  static Type getTypeReference(TypeVariable typeVariable, TypeContext typeContext) {
    String typeParamName = typeVariable.asElement().getSimpleName().toString();
    Optional<ContextTypeParameter> namedTypeReference = typeContext.get(typeParamName);
    if (namedTypeReference.isPresent()) {
      return namedTypeReference.get().namedType();
    }
    throw JavaStatementException.withMessage("Unknown type: {}", typeParamName);
  }

  static Type getTypeReference(TypeMirror typeMirror, Session session) {
    return getTypeReference(typeMirror, TypeContexts.empty(), session);
  }

  static Type getTypeReference(TypeMirror typeMirror, TypeContext typeContext, Session session) {
    return switch (typeMirror.getKind()) {
      case BOOLEAN -> PrimitiveTypeReferences.Boolean;
      case BYTE -> PrimitiveTypeReferences.Byte;
      case SHORT -> PrimitiveTypeReferences.Short;
      case INT -> PrimitiveTypeReferences.Integer;
      case LONG -> PrimitiveTypeReferences.Long;
      case CHAR -> PrimitiveTypeReferences.Char;
      case FLOAT -> PrimitiveTypeReferences.Float;
      case DOUBLE -> PrimitiveTypeReferences.Double;
      case DECLARED -> getTypeReference((DeclaredType) typeMirror, typeContext, session);
      case TYPEVAR -> getTypeReference((TypeVariable) typeMirror, typeContext);
      case WILDCARD -> WildcardTypeReferences.of((WildcardType) typeMirror, typeContext, session);
      case ARRAY -> ArrayTypeReferences.of((ArrayType) typeMirror, typeContext, session);
      default -> throw JavaStatementException.withMessage("Unsupported type mirror kind {}", typeMirror.getKind());
    };
  }

  static CustomStatement getCustomTypeStatement(DeclaredType declaredType, Session session) {
    return getTypeReference(declaredType, session).statement();
  }

  static boolean isCustomType(TypeElement typeElement) {
    return typeElement.getKind() == ElementKind.CLASS ||
        typeElement.getKind() == ElementKind.INTERFACE ||
        typeElement.getKind() == ElementKind.RECORD ||
        typeElement.getKind() == ElementKind.ENUM ||
        typeElement.getKind() == ElementKind.ANNOTATION_TYPE;
  }

  static Class<?> getClass(Type type) {
    if (StatementTypes.PrimitiveType.equals(type.statementType())) {
      return type.asPrimitive().orElseThrow().wrapperClass();
    } else if (StatementTypes.CustomType.equals(type.statementType())) {
      return getClass(type.asCustom().orElseThrow().statement().canonicalName());
    } else {
      throw JavaStatementException.withMessage("Unsupported type {}", type.statementType().typename());
    }
  }

  static java.lang.Class<?> getClass(String className) {
    final Class<?> aClass;
    try {
      aClass = java.lang.Class.forName(className);
    } catch (ClassNotFoundException e) {
      throw new IllegalArgumentException("Class " + className + " is not found", e);
    }
    return aClass;
  }

  static List<TypeBound> getExtendedBounds(
      TypeParameterElement typeParameter, TypeContext typeContext, Session session
  ) {
    List<TypeBound> bounds = new ArrayList<>(typeParameter.getBounds().size());
    for (TypeMirror bound : typeParameter.getBounds()) {
      getBound(bound, typeContext, session).ifPresent(bounds::add);
    }
    return bounds;
  }

  static Optional<TypeBound> getExtendedBound(
      WildcardType wildcardType, TypeContext typeContext, Session session
  ) {
    if (wildcardType.getExtendsBound() == null) {
      return Optional.empty();
    }
    return getBound(wildcardType.getExtendsBound(), typeContext, session);
  }

  static Optional<TypeBound> getSuperBound(
      WildcardType wildcardType, TypeContext typeContext, Session session
  ) {
    if (wildcardType.getSuperBound() == null) {
      return Optional.empty();
    }
    return getBound(wildcardType.getSuperBound(), typeContext, session);
  }

  private static Optional<TypeBound> getBound(
      TypeMirror bound, TypeContext typeContext, Session session
  ) {
    TypeBound boundTypeReference = null;
    if (bound.getKind() == TypeKind.TYPEVAR) {
      TypeVariable typeVariable = (TypeVariable) bound;
      String typeParamName = typeVariable.asElement().getSimpleName().toString();
      NamedType namedTypeReference = typeContext.get(typeParamName)
          .map(ContextTypeParameter::namedType)
          .orElse(null);
      if (namedTypeReference == null) {
        throw JavaStatementException.withMessage("Type variable named {} is not found", typeParamName);
      }
      boundTypeReference = namedTypeReference;
    } else if (bound.getKind() == TypeKind.DECLARED) {
      DeclaredType declaredType = (DeclaredType) bound;
      CustomType customTypeReference = CustomTypeReferences.of(declaredType, typeContext, session);
      if (!Object.class.getName().equals(customTypeReference.statement().canonicalName())) {
        boundTypeReference = customTypeReference;
      }
    } else if (TypeKind.ARRAY == bound.getKind()) {
      boundTypeReference = ArrayTypeReferences.of((ArrayType) bound, typeContext, session);
    } else {
      throw JavaStatementException.withMessage("Unsupported type parameter kind {}", bound.getKind());
    }
    return Optional.ofNullable(boundTypeReference);
  }

  static CustomStatement asCustomStatement(TypeElement typeElement, Session session) {
    return asCustomStatement(typeElement, TypeContexts.empty(), session);
  }

  static CustomStatement asCustomStatement(TypeElement typeElement, TypeContext typeContext, Session session) {
    if (typeElement.getKind() == ElementKind.CLASS) {
      return ClassStatements.of(typeElement, typeContext, session);
    } else if (typeElement.getKind() == ElementKind.INTERFACE) {
      return InterfaceStatements.of(typeElement, typeContext, session);
    } else if (typeElement.getKind() == ElementKind.RECORD) {
      return RecordStatements.of(typeElement, typeContext, session);
    } else if (typeElement.getKind() == ElementKind.ENUM) {
      return EnumStatements.of(typeElement, typeContext, session);
    } else if (typeElement.getKind() == ElementKind.ANNOTATION_TYPE) {
      return AnnotationStatements.of(typeElement, typeContext, session);
    } else {
      throw JavaStatementException.withMessage("Type element of kind can't be represented as custom type statement {}",
          typeElement.getKind());
    }
  }

  @SuppressWarnings("unchecked")
  static <T extends CustomStatement> T asCustomStatement(
      TypeElement typeElement,
      ElementKind expectedElementKind,
      TriFunction<TypeElement, TypeContext, Session, T> factory,
      TypeContext typeContext,
      Session session
  ) {
    if (typeElement.getKind() != expectedElementKind) {
      throw JavaStatementException.withMessage("Expected type element of the kind {} but actual is {}",
          expectedElementKind, typeElement.getKind());
    }

    String typeName = getCanonicalName(typeElement);
    T statement = (T) session.getType(typeName);
    if (statement == null) {
      statement = factory.apply(typeElement, typeContext, session);
      session.putType(typeName, statement);
    }
    return statement;
  }
}
