package intellispaces.javastatements.object.session;

import intellispaces.javastatements.model.custom.CustomType;
import intellispaces.javastatements.model.session.Session;

import java.util.HashMap;
import java.util.Map;

class SessionObject implements Session {
  private final Map<String, CustomType> types = new HashMap<>();

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
