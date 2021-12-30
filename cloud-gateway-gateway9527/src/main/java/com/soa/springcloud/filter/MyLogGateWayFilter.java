
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

//        String uname = exchange.getRequest().getQueryParams().getFirst("uname");
//
//        exchange.getRequest().getCookies();
//        if(uname == null)
//        {
//            log.info("*******用户名为null，非法用户，o(╥﹏╥)o");
//            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
//            return exchange.getResponse().setComplete();
//        }
//        log.info("token:"+exchange.getRequest().getHeaders().get("token").toString());
        RequestPath path = exchange.getRequest().getPath();
        log.info("请求方法："+path);
//        if(path.toString().contains("/user/userinfo")||path.toString().equals("/user/resume")||path.toString().equals("/user/edu")){
//            MultiValueMap<String, HttpCookie> cookies = exchange.getRequest().getCookies();
//            List<HttpCookie> token = cookies.get("token");
//            log.info("登录用户:"+token);
//            if(token!=null) return chain.filter(exchange);
//            log.info("鉴权失败！");
//
//            response.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_UTF8.toString());
//            CommonResult<Object> failure = CommonResult.failure("请重新登录！");
//            byte[] bytes = JSON.toJSONString(failure).getBytes();
//
//            DataBuffer buffer = response.bufferFactory().wrap(bytes);
//            response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
//            return response.writeWith(Mono.just(buffer));
//        }
//        else
//        {

            return chain.filter(exchange);
        //}

    }

    @Override
    public int getOrder()
    {
        return 0;
    }
}

//package com.soa.springcloud.filter;
//
//import cn.hutool.core.date.DateUtil;
//import cn.hutool.json.JSONUtil;
//import org.reactivestreams.Publisher;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.core.io.buffer.DataBufferFactory;
//import org.springframework.core.io.buffer.DataBufferUtils;
//import org.springframework.core.io.buffer.DefaultDataBufferFactory;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.net.InetSocketAddress;
//import java.net.URI;
//import java.nio.charset.Charset;
//import java.util.Map;
//
///**
// * @calssName AppCacheRequestBodyFilter
// * @Description 将 request body 中的内容 copy 一份，记录到 exchange 的一个自定义属性中
// * @Author jiangshaoneng
// * @DATE 2020/9/27 14:42
// */
///**
// * @calssName LogFilter
// * @Description 全局日志打印,请求日志以及返回日志,并在返回结果日志中添加请求时间
// * @Author jiangshaoneng
// * @DATE 2020/9/25 14:54
// */
//@Component
//public class MyLogGateWayFilter implements GlobalFilter, Ordered {
//
//    private static final Logger logger = LoggerFactory.getLogger(MyLogGateWayFilter.class);
//
//    private int order;
//
//    private static final String REQUEST_PREFIX = "\n--------------------------------- Request  Info -----------------------------";
//
//    private static final String REQUEST_TAIL   = "\n-----------------------------------------------------------------------------";
//
//    private static final String RESPONSE_PREFIX = "\n--------------------------------- Response Info -----------------------------";
//
//    private static final String RESPONSE_TAIL   = "\n-------------------------------------------------------------------------->>>";
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        long start = DateUtil.currentSeconds();
//
//        StringBuilder reqMsg = new StringBuilder();
//        StringBuilder resMsg = new StringBuilder();
//        // 获取请求信息
//        ServerHttpRequest request = exchange.getRequest();
//        InetSocketAddress address = request.getRemoteAddress();
//        String method = request.getMethodValue();
//        URI uri = request.getURI();
//        HttpHeaders headers = request.getHeaders();
//        // 获取请求body
//        //Object cachedRequestBodyObject = exchange.getAttributeOrDefault("key", null);
//        //byte[] body = (byte[]) cachedRequestBodyObject;
//        Map<String, Object> body = exchange.getAttributes();
//        String params =JSONUtil.toJsonPrettyStr(body);
//        // 获取请求query
//        Map queryMap = request.getQueryParams();
//        String query = JSONUtil.toJsonPrettyStr(queryMap);
//
//        // 拼接请求日志
//        reqMsg.append(REQUEST_PREFIX);
//        reqMsg.append("\n header=").append(headers);
//        reqMsg.append("\n query=").append(query);
//        reqMsg.append("\n params=").append(params);
//        reqMsg.append("\n address=").append(address.getHostName()).append(address.getPort());
//        reqMsg.append("\n method=").append(method);
//        reqMsg.append("\n url=").append(uri.getPath());
//        reqMsg.append(REQUEST_TAIL);
//        logger.info(reqMsg.toString()); // 打印入参日志
//
//        ServerHttpResponse response = exchange.getResponse();
//        DataBufferFactory bufferFactory = response.bufferFactory();
//        resMsg.append(RESPONSE_PREFIX);
//        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(response) {
//            @Override
//            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
//                if (body instanceof Flux) {
//                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
//                    return super.writeWith(fluxBody.buffer().map(dataBuffer -> {
//                        DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
//                        DataBuffer join = dataBufferFactory.join(dataBuffer);
//                        byte[] content = new byte[join.readableByteCount()];
//                        join.read(content);
//                        DataBufferUtils.release(join);//释放掉内存
//                        String responseResult = new String(content, Charset.forName("UTF-8"));
//                        resMsg.append("\n status=").append(this.getStatusCode());
//                        resMsg.append("\n header=").append(this.getHeaders());
//                        resMsg.append("\n responseResult=").append(responseResult);
//                        resMsg.append(RESPONSE_TAIL);
//
//                        // 计算请求时间
//                        long end = DateUtil.currentSeconds();
//                        long time = end - start;
//                        resMsg.append("耗时ms:").append(time);
//                        logger.info(resMsg.toString()); // 打印结果日志
//                        return bufferFactory.wrap(content);
//                    }));
//                }
//                return super.writeWith(body);
//            }
//        };
//        return chain.filter(exchange.mutate().response(decoratedResponse).build());
//    }
//
//    @Override
//    public int getOrder() {
//        return this.order;
//    }
//
//    /*public GlobalLogFilter(int order){
//        this.order = order;
//    }*/
//}
//
