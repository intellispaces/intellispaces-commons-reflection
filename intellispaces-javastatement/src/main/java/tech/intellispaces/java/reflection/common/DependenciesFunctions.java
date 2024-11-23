package tech.intellispaces.java.reflection.common;

import tech.intellispaces.java.reflection.StatementTypes;
import tech.intellispaces.java.reflection.customtype.CustomType;
import tech.intellispaces.java.reflection.exception.JavaStatementExceptions;
import tech.intellispaces.java.reflection.instance.AnnotationInstance;
import tech.intellispaces.java.reflection.instance.Instance;
import tech.intellispaces.java.reflection.method.MethodParam;
import tech.intellispaces.java.reflection.method.MethodStatement;
import tech.intellispaces.java.reflection.reference.ArrayReference;
import tech.intellispaces.java.reflection.reference.CustomTypeReference;
import tech.intellispaces.java.reflection.reference.NamedReference;
import tech.intellispaces.java.reflection.reference.TypeReference;
import tech.intellispaces.java.reflection.reference.WildcardReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Functions to calculate statement dependencies.
 */
public interface DependenciesFunctions {

  static Collection<CustomType> getCustomTypeDependencies(CustomType type) {
    return getCustomTypeDependencies(type, true, Set.of()).stream()
        .filter(customType -> !customType.canonicalName().startsWith("java.lang."))
        .filter(customType -> !customType.canonicalName().equals(type.canonicalName()))
        .filter(customType -> !customType.packageName().equals(type.packageName()))
        .toList();
  }

  static Collection<CustomType> getTypeReferenceDependencies(TypeReference typeReference) {
    return getTypeReferenceDependencies(typeReference, false, Set.of()).stream()
        .filter(customType -> !customType.canonicalName().startsWith("java.lang."))
        .toList();
  }

  private static List<CustomType> getCustomTypeDependencies(
      CustomType customType, boolean includeRelations, Set<String> exclusions
  ) {
    if (exclusions.contains(customType.canonicalName())) {
      return List.of();
    }
    Set<String> newExclusions = new HashSet<>(exclusions);
    newExclusions.add(customType.canonicalName());

    List<CustomType> customTypes = new ArrayList<>();
    customTypes.add(customType);
    if (includeRelations) {
      customType.annotations().stream()
          .map(ant -> getAnnotationDependencies(ant, exclusions))
          .forEach(customTypes::addAll);
      customType.parentTypes().stream()
          .map(ref -> getCustomTypeReferenceDependencies(ref, false, newExclusions))
          .forEach(customTypes::addAll);
      customType.typeParameters().stream()
          .map(ref -> getNamedTypeReferenceDependencies(ref, false, newExclusions))
          .forEach(customTypes::addAll);
      customType.declaredMethods().stream()
          .map(ref -> getMethodDependencies(ref, newExclusions))
          .forEach(customTypes::addAll);
    }
    return customTypes;
  }

  private static List<CustomType> getMethodDependencies(MethodStatement method, Set<String> exclusions) {
    List<CustomType> customTypes = new ArrayList<>();
    method.typeParameters().stream()
        .map(typeParam -> getNamedTypeReferenceDependencies(typeParam, false, exclusions))
        .forEach(customTypes::addAll);
    if (method.returnType().isPresent()) {
      customTypes.addAll(getTypeReferenceDependencies(method.returnType().orElseThrow(), false, exclusions));
    }
    method.params().stream()
        .map(MethodParam::type)
        .map(paramType -> getTypeReferenceDependencies(paramType, false, exclusions))
        .forEach(customTypes::addAll);
    method.exceptions().stream()
        .map(exceptionType -> getTypeReferenceDependencies(exceptionType, false, exclusions))
        .forEach(customTypes::addAll);
    return customTypes;
  }

  private static List<CustomType> getInstanceDependencies(Instance instance, Set<String> exclusions) {
    if (StatementTypes.ClassInstance == instance.statementType()) {
      CustomType classType = instance.asClass().orElseThrow().type();
      if (!exclusions.contains(classType.canonicalName())) {
        return List.of(classType);
      }
    } else if (StatementTypes.EnumInstance == instance.statementType()) {
      CustomType enumType = instance.asEnum().orElseThrow().type();
      if (!exclusions.contains(enumType.canonicalName())) {
        return List.of(enumType);
      }
    } else if (StatementTypes.ArrayInstance == instance.statementType()) {
      return getTypeReferenceDependencies(instance.asArray().orElseThrow().elementType(), false, exclusions);
    } else if (StatementTypes.AnnotationInstance == instance.statementType()) {
      return getAnnotationDependencies(instance.asAnnotation().orElseThrow(), exclusions);
    }
    return List.of();
  }

  private static List<CustomType> getAnnotationDependencies(
      AnnotationInstance annotation, Set<String> exclusions
  ) {
    List<CustomType> customTypes = new ArrayList<>();
    customTypes.add(annotation.annotationStatement());

    annotation.elements().stream()
        .map(element -> DependenciesFunctions.getInstanceDependencies(element.value(), exclusions))
        .forEach(customTypes::addAll);
    return customTypes;
  }

  private static List<CustomType> getTypeReferenceDependencies(
      TypeReference typeReference, boolean includeRelations, Set<String> exclusions
  ) {
    if (typeReference.isPrimitiveReference()) {
      return List.of();
    } else {
      return switch ((StatementTypes) typeReference.statementType()) {
        case CustomReference -> getCustomTypeReferenceDependencies(
            typeReference.asCustomTypeReference().orElseThrow(), includeRelations, exclusions);
        case NamedReference -> getNamedTypeReferenceDependencies(
            typeReference.asNamedReference().orElseThrow(), includeRelations, exclusions);
        case WildcardReference -> getWildcardTypeReferenceDependencies(
            typeReference.asWildcard().orElseThrow(), includeRelations, exclusions);
        case ArrayReference -> getArrayTypeReferenceDependencies(
            typeReference.asArrayReference().orElseThrow(), includeRelations, exclusions);
        default -> throw JavaStatementExceptions.withMessage("Unsupported statement type {0}", typeReference.statementType());
      };
    }
  }

  private static List<CustomType> getCustomTypeReferenceDependencies(
      CustomTypeReference typeReference, boolean includeRelations, Set<String> exclusions
  ) {
    List<CustomType> customTypes = getCustomTypeDependencies(typeReference.targetType(), includeRelations, exclusions);
    List<CustomType> allCustomTypes = new ArrayList<>(customTypes);
    typeReference.typeArguments().stream()
        .map(ref -> getTypeReferenceDependencies(ref, includeRelations, exclusions))
        .forEach(allCustomTypes::addAll);
    return allCustomTypes;
  }

  private static List<CustomType> getNamedTypeReferenceDependencies(
      NamedReference typeReference, boolean includeParentsAndTypeParams, Set<String> exclusions
  ) {
    return typeReference.extendedBounds().stream()
        .map(ref -> getTypeReferenceDependencies(ref, includeParentsAndTypeParams, exclusions))
        .flatMap(List::stream)
        .toList();
  }

  private static List<CustomType> getWildcardTypeReferenceDependencies(
      WildcardReference typeReference, boolean includeRelations, Set<String> exclusions
  ) {
    List<CustomType> relatedClasses = new ArrayList<>();
    typeReference.extendedBound()
        .map(ref -> getTypeReferenceDependencies(ref, includeRelations, exclusions))
        .ifPresent(relatedClasses::addAll);
    typeReference.superBound()
        .map(ref -> getTypeReferenceDependencies(ref, includeRelations, exclusions))
        .ifPresent(relatedClasses::addAll);
    return relatedClasses;
  }

  private static List<CustomType> getArrayTypeReferenceDependencies(
      ArrayReference typeReference, boolean includeRelations, Set<String> exclusions
  ) {
    if (typeReference.elementType().isPrimitiveReference()) {
      return List.of();
    }
    return getTypeReferenceDependencies(
        typeReference.elementType().asNotPrimitiveReference().orElseThrow(IllegalStateException::new),
        includeRelations,
        exclusions
    );
  }
}
