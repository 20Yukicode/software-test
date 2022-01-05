
package com.soa.springcloud.filter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.soa.springcloud.entities.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
@Component
@Slf4j
public class MyLogGateWayFilter implements GlobalFilter,Ordered
{
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
    {
        ServerHttpResponse response = exchange.getResponse();

        log.info("***********come in MyLogGateWayFilter:  "+new Date());

        RequestPath path = exchange.getRequest().getPath();
        log.info("请求方法："+path);

        if(path.toString().contains("login")||path.toString().contains("register")||path.toString().contains("email")){
            log.info("放行");
            return chain.filter(exchange);
        }
        else
        {
            MultiValueMap<String, HttpCookie> cookies = exchange.getRequest().getCookies();
            List<HttpCookie> token = cookies.get("token");
            log.info("登录用户:"+token);
            if(token!=null) return chain.filter(exchange);
            log.info("鉴权失败！");
            response.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_UTF8.toString());
            CommonResult<Object> failure = CommonResult.failure("用户信息失效，请重新登录！");
            byte[] bytes = JSON.toJSONString(failure).getBytes();

            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return response.writeWith(Mono.just(buffer));
        }

    }

    @Override
    public int getOrder()
    {
        return 0;
    }
}
