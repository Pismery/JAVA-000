package org.pismery.javacourse.rpc.demo.provider;

import org.pismery.javacourse.rpc.core.api.RpcfxRequest;
import org.pismery.javacourse.rpc.core.api.RpcfxResolver;
import org.pismery.javacourse.rpc.core.api.RpcfxResponse;
import org.pismery.javacourse.rpc.core.server.RpcfxInvoker;
import org.pismery.javacourse.rpc.demo.api.OrderService;
import org.pismery.javacourse.rpc.demo.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RpcfxServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RpcfxServerApplication.class, args);
    }

    @Autowired
    RpcfxInvoker invoker;

    @PostMapping("/")
    public RpcfxResponse invoke(@RequestBody RpcfxRequest request) {
        return invoker.invoke(request);
    }

    @Bean
    public RpcfxInvoker createInvoker(@Autowired RpcfxResolver resolver) {
        return new RpcfxInvoker(resolver);
    }

    @Bean
    public RpcfxResolver createResolver() {
        return new DemoResolver();
    }

    // 能否去掉name
    //
    @Bean(name = "org.pismery.javacourse.rpc.demo.api.UserService")
    public UserService createUserService() {
        return new UserServiceImpl();
    }

    @Bean(name = "org.pismery.javacourse.rpc.demo.api.OrderService")
    public OrderService createOrderService() {
        return new OrderServiceImpl();
    }

}
