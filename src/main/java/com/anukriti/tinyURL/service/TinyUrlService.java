package com.anukriti.tinyURL.service;

import com.anukriti.tinyURL.model.IdGenerator;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TinyUrlService {
    private final Map<String, String> longToShortMapping = new HashMap<>();
    private final Map<String, String> shortToLongMapping = new HashMap<>();
    private IdGenerator idGenerator;
    private  long counter = 1;

    public TinyUrlService(IdGenerator idGenerator){
        this.idGenerator = idGenerator;
    }

    public boolean ifTinyUrlExists(String longUrl){
        if(longToShortMapping.containsKey(longUrl)){
            return true;
        }
        return false;
    }

    public String getTinyUrl(String longUrl){
        if(longToShortMapping.containsKey(longUrl)){
            return longToShortMapping.get(longUrl);
        }

        String shortUrl = idGenerator.generate(counter++);

        longToShortMapping.put(longUrl, shortUrl);
        shortToLongMapping.put(shortUrl, longUrl);
        return shortUrl;
    }

    public String getLongUrl(String shortUrl){
        return shortToLongMapping.get(shortUrl);
    }
}
