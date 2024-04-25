package intellispaces.javastatements.statement;

import intellispaces.commons.function.TriFunction;
import intellispaces.javastatements.context.TypeContextBlank;
import intellispaces.javastatements.context.TypeContextBuilder;
import intellispaces.javastatements.exception.JavaStatementException;
import intellispaces.javastatements.context.ContextTypeParameter;
import intellispaces.javastatements.context.TypeContext;
import intellispaces.javastatements.statement.custom.AnnotationFunctions;
import intellispaces.javastatements.statement.custom.AnnotationStatement;
import intellispaces.javastatements.statement.custom.AnnotationStatementBuilder;
import intellispaces.javastatements.statement.custom.ClassStatement;
import intellispaces.javastatements.statement.custom.ClassStatementBuilder;
import intellispaces.javastatements.statement.custom.CustomType;
import intellispaces.javastatements.statement.custom.EnumStatement;
import intellispaces.javastatements.statement.custom.EnumStatementBuilder;
import intellispaces.javastatements.statement.custom.InterfaceStatement;
import intellispaces.javastatements.statement.custom.InterfaceStatementBuilder;
import intellispaces.javastatements.statement.custom.MethodSignature;
import intellispaces.javastatements.statement.custom.MethodSignatureBuilder;
import intellispaces.javastatements.statement.custom.MethodStatement;
import intellispaces.javastatements.statement.custom.RecordStatement;
import intellispaces.javastatements.statement.custom.RecordStatementBuilder;
import intellispaces.javastatements.statement.instance.AnnotationInstance;
import intellispaces.javastatements.statement.reference.ArrayTypeReference;
import intellispaces.javastatements.statement.reference.ArrayTypeReferenceBuilder;
import intellispaces.javastatements.statement.reference.CustomTypeReference;
import intellispaces.javastatements.statement.reference.CustomTypeReferenceBuilder;
import intellispaces.javastatements.statement.reference.NamedTypeReference;
import intellispaces.javastatements.statement.reference.NonPrimitiveTypeReference;
import intellispaces.javastatements.statement.reference.TypeBoundReference;
import intellispaces.javastatements.statement.reference.TypeReference;
import intellispaces.javastatements.session.Session;
import intellispaces.javastatements.statement.custom.MethodStatementBuilder;
import intellispaces.javastatements.statement.reference.NamedTypeReferenceBuilder;
import intellispaces.javastatements.statement.reference.PrimitiveTypeReferences;
import intellispaces.javastatements.statement.reference.WildcardTypeReferenceBuilder;

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
    return getParentTypes(typeElement, TypeContextBuilder.empty(), session);
  }

  static List<CustomTypeReference> getParentTypes(TypeElement typeElement, TypeContext typeContext, Session session) {
    List<TypeMirror> parentTypeMirrors = new ArrayList<>(1 + typeElement.getInterfaces().size());
    TypeMirror superClass = typeElement.getSuperclass();
    if (superClass != null && TypeKind.NONE != superClass.getKind()) {
      parentTypeMirrors.add(superClass);
    }
    parentTypeMirrors.addAll(typeElement.getInterfaces());

    List<NamedTypeReference> thisTypeParams = getTypeParameters(typeElement, typeContext, session);
    TypeContext newNameContext = TypeContextBuilder.get()
        .parentContext(typeContext)
        .addTypeParams(thisTypeParams)
        .build();
    return parentTypeMirrors.stream()
        .map(parent -> asTypeReference((DeclaredType) parent, newNameContext, session))
        .filter(ref -> !Object.class.getName().equals(ref.targetType().canonicalName()))
        .toList();
  }

  static List<NamedTypeReference> getTypeParameters(TypeElement typeElement, Session session) {
    return getTypeParameters(typeElement, TypeContextBuilder.empty(), session);
  }

  static List<NamedTypeReference> getTypeParameters(Parameterizable parameterizable, TypeContext typeContext, Session session) {
    TypeContextBlank nameContextBlank = TypeContextBuilder.blank();
    nameContextBlank.setParentContext(typeContext);
    List<NamedTypeReference> typeParams = parameterizable.getTypeParameters().stream()
        .map(param -> getTypeParameter(param, nameContextBlank, session))
        .toList();
    typeParams.forEach(typeParam -> nameContextBlank.addTypeParam(typeParam.name(), typeParam));
    return typeParams;
  }

  private static NamedTypeReference getTypeParameter(TypeParameterElement typeParameter, TypeContext typeContext, Session session) {
    return NamedTypeReferenceBuilder.build(typeParameter, typeContext, session);
  }

  static List<NonPrimitiveTypeReference> getTypeArguments(DeclaredType declaredType, TypeContext typeContext, Session session) {
    List<NonPrimitiveTypeReference> typeArguments = new ArrayList<>();
    for (TypeMirror typeArgumentsMirror : declaredType.getTypeArguments()) {
      TypeReference typeArgumentsReference = getTypeReference(typeArgumentsMirror, typeContext, session);
      if (typeArgumentsReference instanceof NonPrimitiveTypeReference) {
        typeArguments.add((NonPrimitiveTypeReference) typeArgumentsReference);
      } else {
        throw JavaStatementException.withMessage("Invalid type reference kind");
      }
    }
    return typeArguments;
  }

  static List<AnnotationInstance> getAnnotations(Element typeElement, Session session) {
    return AnnotationFunctions.getAnnotations(typeElement.getAnnotationMirrors(), session);
  }

  static List<MethodStatement> getDeclaredMethods(TypeElement typeElement, CustomType methodOwner, TypeContext typeContext, Session session) {
    return typeElement.getEnclosedElements().stream()
        .filter(element -> element.getKind() == ElementKind.METHOD)
        .map(element -> (ExecutableElement) element)
        .map(element -> MethodStatementBuilder.build(element, methodOwner, typeContext, session))
        .toList();
  }

  static CustomTypeReference getTypeReference(DeclaredType declaredType, Session session) {
    return getTypeReference(declaredType, TypeContextBuilder.empty(), session);
  }

  static CustomTypeReference getTypeReference(DeclaredType declaredType, TypeContext typeContext, Session session) {
    return asTypeReference(declaredType, typeContext, session);
  }

  static TypeReference getTypeReference(TypeVariable typeVariable, TypeContext typeContext) {
    String typeParamName = typeVariable.asElement().getSimpleName().toString();
    Optional<ContextTypeParameter> namedTypeReference = typeContext.get(typeParamName);
    if (namedTypeReference.isPresent()) {
      return namedTypeReference.get().reference();
    }
    throw JavaStatementException.withMessage("Unknown type reference: {}", typeParamName);
  }

  static TypeReference getTypeReference(TypeMirror typeMirror, Session session) {
    return getTypeReference(typeMirror, TypeContextBuilder.empty(), session);
  }

  static TypeReference getTypeReference(TypeMirror typeMirror, TypeContext typeContext, Session session) {
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
      case WILDCARD -> WildcardTypeReferenceBuilder.build((WildcardType) typeMirror, typeContext, session);
      case ARRAY -> asTypeReference((ArrayType) typeMirror, typeContext, session);
      default -> throw JavaStatementException.withMessage("Unsupported type mirror kind {}", typeMirror.getKind());
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
      throw JavaStatementException.withMessage("Unsupported reference type {}", typeReference.statementType().typename());
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

  static List<TypeBoundReference> getExtendedBounds(TypeParameterElement typeParameter, TypeContext typeContext, Session session) {
    List<TypeBoundReference> bounds = new ArrayList<>(typeParameter.getBounds().size());
    for (TypeMirror bound : typeParameter.getBounds()) {
      getBound(bound, typeContext, session).ifPresent(bounds::add);
    }
    return bounds;
  }

  static Optional<TypeBoundReference> getExtendedBound(WildcardType wildcardType, TypeContext typeContext, Session session) {
    if (wildcardType.getExtendsBound() == null) {
      return Optional.empty();
    }
    return getBound(wildcardType.getExtendsBound(), typeContext, session);
  }

  static Optional<TypeBoundReference> getSuperBound(WildcardType wildcardType, TypeContext typeContext, Session session) {
    if (wildcardType.getSuperBound() == null) {
      return Optional.empty();
    }
    return getBound(wildcardType.getSuperBound(), typeContext, session);
  }

  private static Optional<TypeBoundReference> getBound(TypeMirror bound, TypeContext typeContext, Session session) {
    TypeBoundReference boundTypeReference = null;
    if (bound.getKind() == TypeKind.TYPEVAR) {
      TypeVariable typeVariable = (TypeVariable) bound;
      String typeParamName = typeVariable.asElement().getSimpleName().toString();
      NamedTypeReference namedTypeReference = typeContext.get(typeParamName).map(ContextTypeParameter::reference).orElse(null);
      if (namedTypeReference == null) {
        throw JavaStatementException.withMessage("Type variable named {} is not found", typeParamName);
      }
      boundTypeReference = namedTypeReference;
    } else if (bound.getKind() == TypeKind.DECLARED) {
      DeclaredType declaredType = (DeclaredType) bound;
      CustomTypeReference customTypeReference = asTypeReference(declaredType, typeContext, session);
      if (!Object.class.getName().equals(customTypeReference.targetType().canonicalName())) {
        boundTypeReference = customTypeReference;
      }
    } else if (TypeKind.ARRAY == bound.getKind()) {
      boundTypeReference = asTypeReference((ArrayType) bound, typeContext, session);
    } else {
      throw JavaStatementException.withMessage("Unsupported type parameter kind {}", bound.getKind());
    }
    return Optional.ofNullable(boundTypeReference);
  }

  static CustomType asCustomTypeStatement(TypeElement typeElement, Session session) {
    return asCustomTypeStatement(typeElement, TypeContextBuilder.empty(), session);
  }

  static CustomType asCustomTypeStatement(TypeElement typeElement, TypeContext typeContext, Session session) {
    if (typeElement.getKind() == ElementKind.CLASS) {
      return asClassStatement(typeElement, typeContext, session);
    } else if (typeElement.getKind() == ElementKind.INTERFACE) {
      return asInterfaceStatement(typeElement, typeContext, session);
    } else if (typeElement.getKind() == ElementKind.RECORD) {
      return asRecordStatement(typeElement, typeContext, session);
    } else if (typeElement.getKind() == ElementKind.ENUM) {
      return asEnumStatement(typeElement, typeContext, session);
    } else if (typeElement.getKind() == ElementKind.ANNOTATION_TYPE) {
      return asAnnotationStatement(typeElement, typeContext, session);
    } else {
      throw JavaStatementException.withMessage("Type element of kind can't be represented as custom type statement" + typeElement.getKind());
    }
  }

  static ClassStatement asClassStatement(TypeElement typeElement, Session session) {
    return asClassStatement(typeElement, TypeContextBuilder.empty(), session);
  }

  static ClassStatement asClassStatement(TypeElement typeElement, TypeContext typeContext, Session session) {
    return asCustomTypeStatement(typeElement, ElementKind.CLASS, ClassStatementBuilder::build, typeContext, session);
  }

  static InterfaceStatement asInterfaceStatement(TypeElement typeElement, Session session) {
    return asInterfaceStatement(typeElement, TypeContextBuilder.empty(), session);
  }

  static InterfaceStatement asInterfaceStatement(TypeElement typeElement, TypeContext typeContext, Session session) {
    return asCustomTypeStatement(typeElement, ElementKind.INTERFACE, InterfaceStatementBuilder::build, typeContext, session);
  }

  static RecordStatement asRecordStatement(TypeElement typeElement, Session session) {
    return asRecordStatement(typeElement, TypeContextBuilder.empty(), session);
  }

  static RecordStatement asRecordStatement(TypeElement typeElement, TypeContext typeContext, Session session) {
    return asCustomTypeStatement(typeElement, ElementKind.RECORD, RecordStatementBuilder::build, typeContext, session);
  }

  static EnumStatement asEnumStatement(TypeElement typeElement, Session session) {
    return asEnumStatement(typeElement, TypeContextBuilder.empty(), session);
  }

  static EnumStatement asEnumStatement(TypeElement typeElement, TypeContext typeContext, Session session) {
    return asCustomTypeStatement(typeElement, ElementKind.ENUM, EnumStatementBuilder::build, typeContext, session);
  }

  static AnnotationStatement asAnnotationStatement(TypeElement typeElement, Session session) {
    return asAnnotationStatement(typeElement, TypeContextBuilder.empty(), session);
  }

  static AnnotationStatement asAnnotationStatement(TypeElement typeElement, TypeContext typeContext, Session session) {
    return asCustomTypeStatement(typeElement, ElementKind.ANNOTATION_TYPE, AnnotationStatementBuilder::build, typeContext, session);
  }

  @SuppressWarnings("unchecked")
  static <T extends CustomType> T asCustomTypeStatement(
      TypeElement typeElement,
      ElementKind expectedElementKind,
      TriFunction<TypeElement, TypeContext, Session, T> builder,
      TypeContext typeContext,
      Session session
  ) {
    if (typeElement.getKind() != expectedElementKind) {
      throw JavaStatementException.withMessage("Expected type element of the kind {} but actual is {}", expectedElementKind, typeElement.getKind());
    }

    String typeName = getCanonicalName(typeElement);
    T statement = (T) session.getType(typeName);
    if (statement == null) {
      statement = builder.apply(typeElement, typeContext, session);
      session.putType(typeName, statement);
    }
    return statement;
  }

  static CustomTypeReference asTypeReference(TypeElement typeElement, Session session) {
    return asTypeReference(typeElement, TypeContextBuilder.empty(), session);
  }

  static CustomTypeReference asTypeReference(TypeElement typeElement, TypeContext typeContext, Session session) {
    return CustomTypeReferenceBuilder.build(typeElement, typeContext, session);
  }

  static CustomTypeReference asTypeReference(DeclaredType declaredType, Session session) {
    return asTypeReference(declaredType, TypeContextBuilder.empty(), session);
  }

  static CustomTypeReference asTypeReference(DeclaredType declaredType, TypeContext typeContext, Session session) {
    return CustomTypeReferenceBuilder.build(declaredType, typeContext, session);
  }

  static ArrayTypeReference asTypeReference(ArrayType arrayType, Session session) {
    return asTypeReference(arrayType, TypeContextBuilder.empty(), session);
  }

  static ArrayTypeReference asTypeReference(ArrayType arrayType, TypeContext typeContext, Session session) {
    return ArrayTypeReferenceBuilder.build(arrayType, typeContext, session);
  }

  static MethodSignature asMethodSignature(ExecutableElement executableElement, TypeContext typeContext, Session session) {
    return MethodSignatureBuilder.build(executableElement, typeContext, session);
  }
}
