package org.avj.dao;

import java.util.List;

public interface DAO<Entity,Key> {
    void create(Entity e);
    List<Entity> readAll();
    Entity readById(Key key);
    boolean update(Entity e);
    void deleteById(Key key);
}
