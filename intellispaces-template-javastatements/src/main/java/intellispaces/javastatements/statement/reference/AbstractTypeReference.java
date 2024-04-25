package intellispaces.javastatements.statement.reference;

import intellispaces.commons.action.ActionBuilders;
import intellispaces.commons.action.Getter;
import intellispaces.javastatements.statement.DependencyFunctions;
import intellispaces.javastatements.statement.custom.CustomType;

import java.util.Collection;
import java.util.stream.Collectors;

abstract class AbstractTypeReference implements TypeReference {
  private final Getter<Collection<CustomType>> dependenciesGetter;
  private final Getter<Collection<String>> dependencyTypesGetter;
  private final Getter<String> referenceDeclarationGetter;
  private final Getter<String> typeFullDeclarationGetter;
  private final Getter<String> typeBriefDeclarationGetter;

  protected AbstractTypeReference() {
    this.dependenciesGetter = ActionBuilders.cachedLazyGetter(DependencyFunctions::getTypeReferenceDependencies, this);
    this.dependencyTypesGetter = ActionBuilders.cachedLazyGetter(AbstractTypeReference::collectDependencyTypenames, this);
    this.referenceDeclarationGetter = ActionBuilders.cachedLazyGetter(TypeReferenceFunctions::getReferenceDeclaration, this);
    this.typeFullDeclarationGetter = ActionBuilders.cachedLazyGetter(TypeReferenceFunctions::getTypeFullDeclaration, this);
    this.typeBriefDeclarationGetter = ActionBuilders.cachedLazyGetter(TypeReferenceFunctions::getTypeBriefDeclaration, this);
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
  public String referenceDeclaration() {
    return referenceDeclarationGetter.get();
  }

  @Override
  public String typeFullDeclaration() {
    return typeFullDeclarationGetter.get();
  }

  @Override
  public String typeBriefDeclaration() {
    return typeBriefDeclarationGetter.get();
  }
}
