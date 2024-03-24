package intellispaces.javastatements.function;

import intellispaces.javastatements.exception.JavaStatementException;
import intellispaces.javastatements.model.context.ContextTypeParameter;
import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.custom.CustomType;
import intellispaces.javastatements.model.custom.MethodStatement;
import intellispaces.javastatements.model.instance.AnnotationInstance;
import intellispaces.javastatements.model.reference.CustomTypeReference;
import intellispaces.javastatements.model.reference.NamedTypeReference;
import intellispaces.javastatements.model.reference.NonPrimitiveTypeReference;
import intellispaces.javastatements.model.reference.TypeBoundReference;
import intellispaces.javastatements.model.reference.TypeReference;
import intellispaces.javastatements.model.session.Session;
import intellispaces.javastatements.object.StatementTypes;
import intellispaces.javastatements.object.context.NameContextBlank;
import intellispaces.javastatements.object.custom.MethodStatementBuilder;
import intellispaces.javastatements.object.context.NameContextBuilder;
import intellispaces.javastatements.object.reference.NamedTypeReferenceBuilder;
import intellispaces.javastatements.object.reference.PrimitiveTypeReferences;
import intellispaces.javastatements.object.reference.WildcardTypeReferenceBuilder;

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
 * Common type related functions.
 */
public interface TypeFunctions {

  static String getCanonicalName(TypeElement typeElement) {
    return typeElement.getQualifiedName().toString();
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

  static List<CustomTypeReference> getParentTypes(TypeElement typeElement, Session session) {
    return getParentTypes(typeElement, NameContextBuilder.empty(), session);
  }

  static List<CustomTypeReference> getParentTypes(TypeElement typeElement, NameContext nameContext, Session session) {
    List<TypeMirror> parentTypeMirrors = new ArrayList<>(1 + typeElement.getInterfaces().size());
    TypeMirror superClass = typeElement.getSuperclass();
    if (superClass != null && TypeKind.NONE != superClass.getKind()) {
      parentTypeMirrors.add(superClass);
    }
    parentTypeMirrors.addAll(typeElement.getInterfaces());

    List<NamedTypeReference> thisTypeParams = getTypeParameters(typeElement, nameContext, session);
    NameContext newNameContext = NameContextBuilder.get()
        .parentContext(nameContext)
        .addTypeParams(thisTypeParams)
        .build();
    return parentTypeMirrors.stream()
        .map(parent -> ElementFunctions.asTypeReference((DeclaredType) parent, newNameContext, session))
        .filter(ref -> !Object.class.getName().equals(ref.targetType().canonicalName()))
        .toList();
  }

  static List<NamedTypeReference> getTypeParameters(TypeElement typeElement, Session session) {
    return getTypeParameters(typeElement, NameContextBuilder.empty(), session);
  }

  static List<NamedTypeReference> getTypeParameters(Parameterizable parameterizable, NameContext nameContext, Session session) {
    NameContextBlank nameContextBlank = NameContextBuilder.blank();
    nameContextBlank.setParentContext(nameContext);
    List<NamedTypeReference> typeParams = parameterizable.getTypeParameters().stream()
        .map(param -> getTypeParameter(param, nameContextBlank, session))
        .toList();
    typeParams.forEach(typeParam -> nameContextBlank.addTypeParam(typeParam.name(), typeParam));
    return typeParams;
  }

  private static NamedTypeReference getTypeParameter(TypeParameterElement typeParameter, NameContext nameContext, Session session) {
    return NamedTypeReferenceBuilder.build(typeParameter, nameContext, session);
  }

  static List<NonPrimitiveTypeReference> getTypeArguments(DeclaredType declaredType, NameContext nameContext, Session session) {
    List<NonPrimitiveTypeReference> typeArguments = new ArrayList<>();
    for (TypeMirror typeArgumentsMirror : declaredType.getTypeArguments()) {
      TypeReference typeArgumentsReference = getTypeReference(typeArgumentsMirror, nameContext, session);
      if (typeArgumentsReference instanceof NonPrimitiveTypeReference) {
        typeArguments.add((NonPrimitiveTypeReference) typeArgumentsReference);
      } else {
        throw new JavaStatementException("Invalid type reference kind");
      }
    }
    return typeArguments;
  }

  static List<AnnotationInstance> getAnnotations(Element typeElement, Session session) {
    return AnnotationFunctions.getAnnotations(typeElement.getAnnotationMirrors(), session);
  }

  static List<MethodStatement> getDeclaredMethods(TypeElement typeElement, CustomType methodOwner, NameContext nameContext, Session session) {
    return typeElement.getEnclosedElements().stream()
        .filter(element -> element.getKind() == ElementKind.METHOD)
        .map(element -> (ExecutableElement) element)
        .map(element -> MethodStatementBuilder.build(element, methodOwner, nameContext, session))
        .toList();
  }

  static CustomTypeReference getTypeReference(DeclaredType declaredType, Session session) {
    return getTypeReference(declaredType, NameContextBuilder.empty(), session);
  }

  static CustomTypeReference getTypeReference(DeclaredType declaredType, NameContext nameContext, Session session) {
    return ElementFunctions.asTypeReference(declaredType, nameContext, session);
  }

  static TypeReference getTypeReference(TypeVariable typeVariable, NameContext nameContext) {
    String typeParamName = typeVariable.asElement().getSimpleName().toString();
    Optional<ContextTypeParameter> namedTypeReference = nameContext.get(typeParamName);
    if (namedTypeReference.isPresent()) {
      return namedTypeReference.get().type();
    }
    throw new JavaStatementException("Unknown type reference: {}", typeParamName);
  }

  static TypeReference getTypeReference(TypeMirror typeMirror, Session session) {
    return getTypeReference(typeMirror, NameContextBuilder.empty(), session);
  }

  static TypeReference getTypeReference(TypeMirror typeMirror, NameContext nameContext, Session session) {
    return switch (typeMirror.getKind()) {
      case BOOLEAN -> PrimitiveTypeReferences.Boolean;
      case BYTE -> PrimitiveTypeReferences.Byte;
      case SHORT -> PrimitiveTypeReferences.Short;
      case INT -> PrimitiveTypeReferences.Integer;
      case LONG -> PrimitiveTypeReferences.Long;
      case CHAR -> PrimitiveTypeReferences.Char;
      case FLOAT -> PrimitiveTypeReferences.Float;
      case DOUBLE -> PrimitiveTypeReferences.Double;
      case DECLARED -> getTypeReference((DeclaredType) typeMirror, nameContext, session);
      case TYPEVAR -> getTypeReference((TypeVariable) typeMirror, nameContext);
      case WILDCARD -> WildcardTypeReferenceBuilder.build((WildcardType) typeMirror, nameContext, session);
      case ARRAY -> ElementFunctions.asTypeReference((ArrayType) typeMirror, nameContext, session);
      default -> throw new JavaStatementException("Unsupported type mirror kind {}", typeMirror.getKind());
    };
  }

  static CustomType getCustomTypeStatement(DeclaredType declaredType, Session session) {
    return getTypeReference(declaredType, session).targetType();
  }

  static boolean isCustomType(TypeElement typeElement) {
    return typeElement.getKind() == ElementKind.CLASS ||
        typeElement.getKind() == ElementKind.INTERFACE ||
        typeElement.getKind() == ElementKind.RECORD ||
        typeElement.getKind() == ElementKind.ENUM ||
        typeElement.getKind() == ElementKind.ANNOTATION_TYPE;
  }

  static Class<?> getClass(TypeReference typeReference) {
    if (StatementTypes.PrimitiveReference.equals(typeReference.statementType())) {
      return typeReference.asPrimitiveTypeReference().orElseThrow().wrapperClass();
    } else if (StatementTypes.CustomTypeReference.equals(typeReference.statementType())) {
      return getClass(typeReference.asCustomTypeReference().orElseThrow().targetType().canonicalName());
    } else {
      throw new JavaStatementException("Unsupported reference type {}", typeReference.statementType().typename());
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

  static List<TypeBoundReference> getExtendedBounds(TypeParameterElement typeParameter, NameContext nameContext, Session session) {
    List<TypeBoundReference> bounds = new ArrayList<>(typeParameter.getBounds().size());
    for (TypeMirror bound : typeParameter.getBounds()) {
      getBound(bound, nameContext, session).ifPresent(bounds::add);
    }
    return bounds;
  }

  static Optional<TypeBoundReference> getExtendedBound(WildcardType wildcardType, NameContext nameContext, Session session) {
    if (wildcardType.getExtendsBound() == null) {
      return Optional.empty();
    }
    return getBound(wildcardType.getExtendsBound(), nameContext, session);
  }

  static Optional<TypeBoundReference> getSuperBound(WildcardType wildcardType, NameContext nameContext, Session session) {
    if (wildcardType.getSuperBound() == null) {
      return Optional.empty();
    }
    return getBound(wildcardType.getSuperBound(), nameContext, session);
  }

  private static Optional<TypeBoundReference> getBound(TypeMirror bound, NameContext nameContext, Session session) {
    TypeBoundReference boundTypeReference = null;
    if (bound.getKind() == TypeKind.TYPEVAR) {
      TypeVariable typeVariable = (TypeVariable) bound;
      String typeParamName = typeVariable.asElement().getSimpleName().toString();
      NamedTypeReference namedTypeReference = nameContext.get(typeParamName).map(ContextTypeParameter::type).orElse(null);
      if (namedTypeReference == null) {
        throw new JavaStatementException("Type variable named {} is not found", typeParamName);
      }
      boundTypeReference = namedTypeReference;
    } else if (bound.getKind() == TypeKind.DECLARED) {
      DeclaredType declaredType = (DeclaredType) bound;
      CustomTypeReference customTypeReference = ElementFunctions.asTypeReference(declaredType, nameContext, session);
      if (!Object.class.getName().equals(customTypeReference.targetType().canonicalName())) {
        boundTypeReference = customTypeReference;
      }
    } else if (TypeKind.ARRAY == bound.getKind()) {
      boundTypeReference = ElementFunctions.asTypeReference((ArrayType) bound, nameContext, session);
    } else {
      throw new JavaStatementException("Unsupported type parameter kind {}", bound.getKind());
    }
    return Optional.ofNullable(boundTypeReference);
  }
}
