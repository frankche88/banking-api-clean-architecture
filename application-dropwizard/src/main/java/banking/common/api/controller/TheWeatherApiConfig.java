package banking.common.api.controller;

import javax.ws.rs.ext.Provider;

import io.swagger.annotations.ApiKeyAuthDefinition;
import io.swagger.annotations.ApiKeyAuthDefinition.ApiKeyLocation;
import io.swagger.annotations.SecurityDefinition;
import io.swagger.annotations.SwaggerDefinition;

//@SwaggerDefinition(
//        info = @Info(
//                description = "Gets the weather",
//                version = "V12.0.12",
//                title = "The Weather API",
//                termsOfService = "http://theweatherapi.io/terms.html",
//                contact = @Contact(
//                   name = "Rain Moore", 
//                   email = "rain.moore@theweatherapi.io", 
//                   url = "http://theweatherapi.io"
//                ),
//                license = @License(
//                   name = "Apache 2.0", 
//                   url = "http://www.apache.org/licenses/LICENSE-2.0"
//                )
//        ),
//        consumes = {"application/json", "application/xml"},
//        produces = {"application/json", "application/xml"},
//        schemes = {SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS},
//        tags = {
//                @Tag(name = "Private", description = "Tag used to denote operations as private")
//        }, 
//        externalDocs = @ExternalDocs(value = "Meteorology", url = "http://theweatherapi.io/meteorology.html")
//)

@SwaggerDefinition(securityDefinition = @SecurityDefinition(apiKeyAuthDefinitions = { @ApiKeyAuthDefinition(key = "ApiKey", name = "Authorization", in = ApiKeyLocation.HEADER) }))
@Provider
public interface TheWeatherApiConfig {

}
