/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sipenta.utils;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 *
 * @author ASUS
 */
public class MongoManager {
    private static MongoClient mongoClient;
    private static final String DATABASE_NAME = "db_perusahaan";
    
    public static MongoDatabase getDatabase() {

        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );
        
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new com.mongodb.ConnectionString("mongodb://localhost:27017"))
                .codecRegistry(pojoCodecRegistry)
                .build();

        mongoClient = MongoClients.create(settings);
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            return database;
    }
}