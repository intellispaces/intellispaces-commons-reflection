package tech.intellispaces.reflection.customtype;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import tech.intellispaces.actions.cache.CachedSupplierActions;
import tech.intellispaces.actions.supplier.SupplierAction;
import tech.intellispaces.reflection.StatementType;
import tech.intellispaces.reflection.StatementTypes;
import tech.intellispaces.reflection.method.MethodStatement;
import tech.intellispaces.reflection.reference.CustomTypeReference;
import tech.intellispaces.reflection.reference.NotPrimitiveReference;

class EffectiveClassType extends AbstractEffectiveCustomType implements ClassType {
  private final SupplierAction<Optional<CustomTypeReference>> extendedClassGetter;
  private final SupplierAction<List<CustomTypeReference>> implementedInterfacesGetter;

  EffectiveClassType(
      ClassType classStatement, Map<String, NotPrimitiveReference> typeMapping
  ) {
    super(classStatement, typeMapping);
    this.extendedClassGetter = CachedSupplierActions.get(CustomTypeFunctions::getExtendedClass, this);
    this.implementedInterfacesGetter = CachedSupplierActions.get(CustomTypeFunctions::getImplementedInterfaces, this);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Class;
  }

  @Override
  public List<MethodStatement> constructors() {
    List<MethodStatement> actualConstructors = ((ClassType) actualType).constructors();
    return actualConstructors.stream()
        .map(c -> c.effective(typeMapping))
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
