package vn.edu.likelion.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import vn.edu.likelion.entity.Category;
import vn.edu.likelion.entity.Course;
import vn.edu.likelion.entity.CourseDetail;
import vn.edu.likelion.entity.InformationType;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
    private String theAllowedOrigins = "https://onlinecourse.up.railway.app/";

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config,
                                                     CorsRegistry corsRegistry) {
        HttpMethod[] theUnsupportedActions = {HttpMethod.POST, HttpMethod.GET,
                HttpMethod.DELETE, HttpMethod.PUT};

        // Danh sách các lớp mà bạn muốn áp dụng cấu hình
        Class<?>[] domainTypes = {Category.class, Course.class, CourseDetail.class, InformationType.class};

        // Áp dụng cấu hình cho từng lớp trong danh sách
        for (Class<?> domainType : domainTypes) {
            config.exposeIdsFor(domainType);
            disableHttpMethods(domainType, config, theUnsupportedActions);
        }

        // Cấu hình CORS
        corsRegistry.addMapping(config.getBasePath() + "/**")
                .allowedOrigins(theAllowedOrigins);
    }

    private <T> void disableHttpMethods(Class<T> theClass, RepositoryRestConfiguration configuration,
                                        HttpMethod[] theUnsupportedActions) {
        configuration.getExposureConfiguration()
                .forDomainType(theClass)
                .withAssociationExposure((metadata, httpMethods) ->
                        httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metadata, httpMethods) ->
                        httpMethods.disable(theUnsupportedActions));
    }
}
