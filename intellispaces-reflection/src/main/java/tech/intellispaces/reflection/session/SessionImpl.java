package tech.intellispaces.reflection.session;

import tech.intellispaces.reflection.customtype.CustomType;

import java.util.HashMap;
import java.util.Map;

class SessionImpl implements Session {
  private final Map<String, CustomType> types = new HashMap<>();

  SessionImpl() {}

  @Override
  public CustomType getType(String typeName) {
    return types.get(typeName);
  }

  @Override
  public void putType(String typeName, CustomType customType) {
    types.put(typeName, customType);
  }

  @Override
  public void clear() {
    types.clear();
  }
}
