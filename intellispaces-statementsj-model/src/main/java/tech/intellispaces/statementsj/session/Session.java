package tech.intellispaces.statementsj.session;

import tech.intellispaces.statementsj.customtype.CustomType;

/**
 * Parsing session.
 */
public interface Session {

  CustomType getType(String typeName);

  void putType(String typeName, CustomType customType);

  void clear();
}
