package dao;

import java.util.List;

interface DAO<T> {
	  void insert(T obj);
	  T getById(String id);
	  T getByName(String name);
	  void update(T obj);
	  void delete(T obj);
	  List<T> getAll();
}
