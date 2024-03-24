package intellispaces.javastatements.object.session;

import intellispaces.javastatements.model.session.Session;

public interface SessionFactory {

  static Session create() {
    return new SessionObject();
  }
}
