package ai.zgc.grpcbootclient.controller;

import ai.zgc.HelloProto;
import ai.zgc.HelloServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GrpcClient("hello")
    private HelloServiceGrpc.HelloServiceBlockingStub stub;

    @GetMapping("/hello")
    public String sayHello(@RequestParam String name) {
        System.out.println("name: " + name);
        if (stub == null) {
            throw new IllegalStateException("gRPC stub 注入失败，请检查 @GrpcClient(\"hello\") 配置和 gRPC 服务名是否一致");
        }
        HelloProto.HelloResponse response = stub.hello(HelloProto.HelloRequest.newBuilder().setName(name).build());
        return response.getResult();
    }
}