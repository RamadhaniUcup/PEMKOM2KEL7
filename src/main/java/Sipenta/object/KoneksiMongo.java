/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sipenta.object;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;


/**
 *
 * @author ASUS
 */
public class KoneksiMongo {
    private static final String URL = "mongodb://localhost:27017";
    
    public static MongoDatabase getDatabase() {
         MongoClient mongoClient = MongoClients.create(URL);

        MongoDatabase database =
                mongoClient.getDatabase("db_perusahaan");

        return database;
    }
}
