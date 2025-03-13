package tech.intellispaces.commons.reflection.customtype;

import tech.intellispaces.commons.action.cache.CachedSupplierActions;
import tech.intellispaces.commons.action.supplier.SupplierAction;
import tech.intellispaces.commons.reflection.StatementType;
import tech.intellispaces.commons.reflection.StatementTypes;
import tech.intellispaces.commons.reflection.method.MethodStatement;
import tech.intellispaces.commons.reflection.reference.CustomTypeReference;
import tech.intellispaces.commons.reflection.reference.NotPrimitiveReference;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
