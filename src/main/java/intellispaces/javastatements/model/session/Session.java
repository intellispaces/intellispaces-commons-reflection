package intellispaces.javastatements.model.session;

import intellispaces.javastatements.model.custom.CustomType;

/**
 * Java Statement parsing session.
 */
public interface Session {

  CustomType getType(String typeName);

  void putType(String typeName, CustomType customType);

  void clear();
}
