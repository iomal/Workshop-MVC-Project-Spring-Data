package app.ccb.config.common.parsers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class GsonImpl implements GsonParser {

    static Gson gson;

    static {
        gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    @Override
    public String toString(Object object) {

        return gson.toJson(object);
    }

    @Override
    public <T> T fromString(String str, Class<T> klass) {
        return gson.fromJson(str,klass);
    }
}
