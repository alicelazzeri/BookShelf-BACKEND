package it.alicelazzeri.book_shelf_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cloudinary.Cloudinary;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinaryUploader( @Value("${cloudinary.name}") String name,
                                          @Value("${cloudinary.apikey}") String apikey,
                                          @Value("${cloudinary.secret}") String secret) {
        Map<String, String> configuration = new HashMap<>();
        configuration.put("cloud_name",name);
        configuration.put("api_key",apikey);
        configuration.put("api_secret",secret);
        return new Cloudinary(configuration);

    }
}
