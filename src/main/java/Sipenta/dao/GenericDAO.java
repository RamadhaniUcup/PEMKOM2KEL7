package sipenta.dao;

import Sipenta.Util.MongoManager;
import com.mongodb.client.MongoCollection;
import org.bson.conversions.Bson;
import java.util.ArrayList;
import java.util.List;

public class GenericDAO<T> implements BaseDao<T> {
    private final MongoCollection<T> collection;
    private final Class<T> clazz;

    // Konstruktor menerima nama koleksi dan kelas entitas untuk mapping otomatis
    public GenericDAO(String collectionName, Class<T> clazz) {
        this.clazz = clazz;
        // Mengambil koleksi dengan dukungan POJO (Plain Old Java Object)
        this.collection = MongoManager.getDatabase().getCollection(collectionName, clazz);
    }

    @Override
    public void save(T entity) {
        try {
            collection.insertOne(entity);
        } catch (Exception e) {
            System.err.println("Error saat save: " + e.getMessage());
        }
    }

    @Override
    public void update(Bson filter, T entity) {
        try {
            collection.replaceOne(filter, entity);
        } catch (Exception e) {
            System.err.println("Error saat update: " + e.getMessage());
        }
    }

    @Override
    public void delete(Bson filter) {
        try {
            collection.deleteOne(filter);
        } catch (Exception e) {
            System.err.println("Error saat delete: " + e.getMessage());
        }
    }

    @Override
    public List<T> findAll() {
        return collection.find().into(new ArrayList<>());
    }

    @Override
    public T findOne(Bson filter) {
        return collection.find(filter).first();
    }

    @Override
    public List<T> findMany(Bson filter) {
        return collection.find(filter).into(new ArrayList<>());
    }
}