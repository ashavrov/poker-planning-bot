package dao;

import java.util.List;

public interface DAO<T> {
	  public abstract void insert(T obj);
	  public abstract T getById(String id);
	  public abstract T getByName(String name);
	  public abstract void update(T obj);
	  public abstract void delete(T obj);
	  public abstract List<T> getAll();
}
