package tech.intellispaces.framework.javastatements.session;

import tech.intellispaces.framework.javastatements.statement.custom.CustomStatement;

import java.util.HashMap;
import java.util.Map;

class SessionImpl implements Session {
  private final Map<String, CustomStatement> types = new HashMap<>();

  SessionImpl() {}

  @Override
  public CustomStatement getType(String typeName) {
    return types.get(typeName);
  }

  @Override
  public void putType(String typeName, CustomStatement customStatement) {
    types.put(typeName, customStatement);
  }

  @Override
  public void clear() {
    types.clear();
  }
}
