package dev.urmat.taskmega;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClient;

@Slf4j
public class ObjectHttpClient {

    public static final String OBJECTS_URL = "https://api.restful-api.dev/objects";
    private final RestClient restClient;

    public ObjectHttpClient() {
        this.restClient = RestClient.create(OBJECTS_URL);
    }

    public void getObjects() {
        ObjectInfo[] objectInfos = restClient.get()
                .retrieve()
                .body(ObjectInfo[].class);

        assert objectInfos != null;
        log.info("Total objects: {}", objectInfos.length);
    }

    public record ObjectInfo(
            String id,
            String name,
            Details data
    ) {
        public record Details(
                String color,
                String capacity
        ) {
        }
    }
}