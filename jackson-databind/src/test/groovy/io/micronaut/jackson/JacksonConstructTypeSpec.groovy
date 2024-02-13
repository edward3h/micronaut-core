package io.micronaut.jackson

import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.context.ApplicationContext
import io.micronaut.context.DefaultApplicationContext
import io.micronaut.core.type.Argument
import spock.lang.Specification

class JacksonConstructTypeSpec extends Specification {
    void "verify that a type is constructed for Map String,String"() {
        given:
        ApplicationContext applicationContext = new DefaultApplicationContext("test").start()
        ObjectMapper objectMapper = applicationContext.getBean(ObjectMapper.class)
        Argument<Map<String,String>> mapArgument = Argument.mapOf(String, String)

        when:
        Argument<Map<String,String>> derivedArgument = Argument.of(mapArgument.asParameterizedType()) as Argument<Map<String, String>>
        JavaType javaType = JacksonConfiguration.constructType(derivedArgument, objectMapper.typeFactory)

        then:
        javaType.mapLikeType

        cleanup:
        applicationContext?.close()
    }
}
