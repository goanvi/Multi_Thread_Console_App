package dao;

import view.command.exceptions.ExecuteCommandException;

import java.util.TreeSet;

public interface DAO<T> {
    TreeSet<T> getAll(String query)throws ExecuteCommandException;

    T getById (String id) throws ExecuteCommandException;

    TreeSet<T> getByOwner(int id) throws ExecuteCommandException;

    T create(T object)throws ExecuteCommandException;

    void update(T object, String id)throws ExecuteCommandException;

    void deleteById(String id)throws ExecuteCommandException;

    void deleteByOwner(String id)throws ExecuteCommandException;

}
