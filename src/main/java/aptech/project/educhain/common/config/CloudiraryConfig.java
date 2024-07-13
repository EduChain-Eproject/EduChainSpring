package aptech.project.educhain.common.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudiraryConfig {
    @Bean
    public Cloudinary cloudinary(){
        final Map<String,String> config = new HashMap<>();
        config.put("cloud_name","dcxzqj0ta");
        config.put("api_key","384387347337941");
        config.put("api_secret","1ALx_UFR70Sin9_zEsZYKrQPR14");
        return new Cloudinary(config);
    }


}
