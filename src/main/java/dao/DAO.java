package dao;

import java.util.ArrayList;

public abstract class DAO<T> {
	  public abstract void insert(T obj);
	  public abstract T getById(String id);
	  public abstract void update(T obj);
	  public abstract void delete(T obj);
	  public abstract ArrayList<T> getAll();
}
