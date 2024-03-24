package intellispaces.javastatements.object.reference;

import intellispaces.commons.function.ActionFunctions;
import intellispaces.commons.model.action.GetterAction;
import intellispaces.javastatements.function.DeclarationFunctions;
import intellispaces.javastatements.function.DependencyFunctions;
import intellispaces.javastatements.model.custom.CustomType;
import intellispaces.javastatements.model.reference.TypeReference;

import java.util.Collection;
import java.util.stream.Collectors;

abstract class AbstractTypeReference implements TypeReference {
  private final GetterAction<Collection<CustomType>> dependenciesGetter;
  private final GetterAction<Collection<String>> dependencyTypesGetter;
  private final GetterAction<String> referenceDeclarationGetter;
  private final GetterAction<String> typeFullDeclarationGetter;
  private final GetterAction<String> typeBriefDeclarationGetter;

  protected AbstractTypeReference() {
    this.dependenciesGetter = ActionFunctions.cachedLazyGetter(DependencyFunctions::getTypeReferenceDependencies, this);
    this.dependencyTypesGetter = ActionFunctions.cachedLazyGetter(AbstractTypeReference::collectDependencyTypenames, this);
    this.referenceDeclarationGetter = ActionFunctions.cachedLazyGetter(DeclarationFunctions::getReferenceDeclaration, this);
    this.typeFullDeclarationGetter = ActionFunctions.cachedLazyGetter(DeclarationFunctions::getTypeFullDeclaration, this);
    this.typeBriefDeclarationGetter = ActionFunctions.cachedLazyGetter(DeclarationFunctions::getTypeBriefDeclaration, this);
  }

  @Override
  public Collection<CustomType> dependencies() {
    return dependenciesGetter.execute();
  }

  @Override
  public Collection<String> dependencyTypenames() {
    return dependencyTypesGetter.execute();
  }

  private Collection<String> collectDependencyTypenames() {
    return dependencies().stream()
        .map(CustomType::canonicalName)
        .collect(Collectors.toSet());
  }

  @Override
  public String referenceDeclaration() {
    return referenceDeclarationGetter.execute();
  }

  @Override
  public String typeFullDeclaration() {
    return typeFullDeclarationGetter.execute();
  }

  @Override
  public String typeBriefDeclaration() {
    return typeBriefDeclarationGetter.execute();
  }
}
