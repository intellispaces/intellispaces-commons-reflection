package intellispaces.common.javastatement.reference;

import intellispaces.common.action.Actions;
import intellispaces.common.action.getter.Getter;
import intellispaces.common.javastatement.customtype.CustomType;
import intellispaces.common.javastatement.common.DependenciesFunctions;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

abstract class AbstractTypeReference implements TypeReference {
  private final Getter<Collection<CustomType>> dependenciesGetter;
  private final Getter<Collection<String>> dependencyTypesGetter;
  private final Getter<String> actualSimpleGetter;
  private final Getter<String> actualDeclarationGetter;
  private final Getter<String> formalFullDeclarationGetter;
  private final Getter<String> formalBriefDeclarationGetter;

  AbstractTypeReference() {
    this.dependenciesGetter = Actions.cachedLazyGetter(DependenciesFunctions::getTypeReferenceDependencies, this);
    this.dependencyTypesGetter = Actions.cachedLazyGetter(AbstractTypeReference::collectDependencyTypenames, this);
    this.actualSimpleGetter = Actions.cachedLazyGetter(TypeReferenceFunctions::getSimpleTypeDeclaration, this);
    this.actualDeclarationGetter = Actions.cachedLazyGetter(TypeReferenceFunctions::getActualTypeDeclaration, this);
    this.formalFullDeclarationGetter = Actions.cachedLazyGetter(TypeReferenceFunctions::getFormalFullTypeReferenceDeclaration, this);
    this.formalBriefDeclarationGetter = Actions.cachedLazyGetter(TypeReferenceFunctions::getFormalBriefTypeReferenceDeclaration, this);
  }

  @Override
  public Collection<CustomType> dependencies() {
    return dependenciesGetter.get();
  }

  @Override
  public Collection<String> dependencyTypenames() {
    return dependencyTypesGetter.get();
  }

  private Collection<String> collectDependencyTypenames() {
    return dependencies().stream()
        .map(CustomType::canonicalName)
        .collect(Collectors.toSet());
  }

  @Override
  public String simpleDeclaration() {
    return actualSimpleGetter.get();
  }

  @Override
  public String simpleDeclaration(Function<String, String> nameMapper) {
    return TypeReferenceFunctions.getSimpleTypeDeclaration(this, nameMapper);
  }

  @Override
  public String actualDeclaration() {
    return actualDeclarationGetter.get();
  }

  @Override
  public String actualDeclaration(Function<String, String> nameMapper) {
    return TypeReferenceFunctions.getActualTypeDeclaration(this, nameMapper);
  }

  @Override
  public String actualBlindDeclaration(Function<String, String> nameMapper) {
    return TypeReferenceFunctions.getActualBlindTypeReferenceDeclaration(this, nameMapper);
  }

  @Override
  public String formalFullDeclaration() {
    return formalFullDeclarationGetter.get();
  }

  @Override
  public String formalBriefDeclaration() {
    return formalBriefDeclarationGetter.get();
  }
}
