package tech.intellispaces.javastatements.customtype;

import tech.intellispaces.actions.Actions;
import tech.intellispaces.actions.getter.Getter;
import tech.intellispaces.javastatements.StatementType;
import tech.intellispaces.javastatements.StatementTypes;
import tech.intellispaces.javastatements.method.MethodStatement;
import tech.intellispaces.javastatements.reference.CustomTypeReference;
import tech.intellispaces.javastatements.reference.NotPrimitiveReference;

import java.util.List;
import java.util.Map;
import java.util.Optional;

class EffectiveClassType extends AbstractEffectiveCustomType implements ClassType {
  private final Getter<Optional<CustomTypeReference>> extendedClassGetter;
  private final Getter<List<CustomTypeReference>> implementedInterfacesGetter;

  EffectiveClassType(
      ClassType classStatement, Map<String, NotPrimitiveReference> typeMapping
  ) {
    super(classStatement, typeMapping);
    this.extendedClassGetter = Actions.cachedLazyGetter(CustomTypeFunctions::getExtendedClass, this);
    this.implementedInterfacesGetter = Actions.cachedLazyGetter(CustomTypeFunctions::getImplementedInterfaces, this);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Class;
  }

  @Override
  public List<MethodStatement> constructors() {
    List<MethodStatement> actualConstructors = ((ClassType) actualType).constructors();
    return actualConstructors.stream()
        .map(c -> c.specify(typeMapping))
        .toList();
  }

  @Override
  public Optional<CustomTypeReference> extendedClass() {
    return extendedClassGetter.get();
  }

  @Override
  public List<CustomTypeReference> implementedInterfaces() {
    return implementedInterfacesGetter.get();
  }
}
