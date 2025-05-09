package com.ssafy.home.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

//@Configuration
public class RedisConfig {
	
	@Value("${spring.data.redis.host}")
	private String host;
	
	@Value("${spring.data.redis.port}")
	private int port;
	
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(host, port);
	}
	
	@Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());

        // Key 직렬화는 StringRedisSerializer 사용
        template.setKeySerializer(new StringRedisSerializer());

        // Value 직렬화는 House 객체를 JSON으로 변환할 수 있도록 Jackson2JsonRedisSerializer 사용
        Jackson2JsonRedisSerializer<Object> jacksonSerializer =
                new Jackson2JsonRedisSerializer<>(Object.class);
        // ObjectMapper 설정 (필요하면 추가 설정)
        ObjectMapper objectMapper = new ObjectMapper();
        jacksonSerializer.setObjectMapper(objectMapper);
        template.setValueSerializer(jacksonSerializer);
        
        // Hash Key/Value 설정도 변경
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jacksonSerializer);
        
        // 기본 serializer 설정
        template.setDefaultSerializer(jacksonSerializer);
        template.afterPropertiesSet();
        
        return template;
    }
	 
	 /**
	     * 리스트에 접근하여 다양한 연산을 수행합니다.
	     *
	     * @return ListOperations<String, Object>
	     */
	    public ListOperations<String, Object> getListOperations() {
	        return this.redisTemplate().opsForList();
	    }

	    /**
	     * 단일 데이터에 접근하여 다양한 연산을 수행합니다.
	     *
	     * @return ValueOperations<String, Object>
	     */
	    public ValueOperations<String, Object> getValueOperations() {
	        return this.redisTemplate().opsForValue();
	    }


	    /**
	     * Redis 작업중 등록, 수정, 삭제에 대해서 처리 및 예외처리를 수행합니다.
	     *
	     * @param operation
	     * @return
	     */
	    public int executeOperation(Runnable operation) {
	        try {
	            operation.run();
	            return 1;
	        } catch (Exception e) {
	            System.out.println("Redis 작업 오류 발생 :: " + e.getMessage());
	            return 0;
	        }
	    }
}
