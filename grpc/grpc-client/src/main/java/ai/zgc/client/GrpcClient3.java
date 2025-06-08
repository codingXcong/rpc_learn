package ai.zgc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;

/**
 * 阻塞式(newBlockingStub) 服务端流式RPC客户端
 */
public class GrpcClient3 {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080).usePlaintext().build();
        try {
            HelloServiceGrpc.HelloServiceBlockingStub stub = HelloServiceGrpc.newBlockingStub(channel);
            HelloProto.HelloRequest.Builder builder = HelloProto.HelloRequest.newBuilder();
            builder.setName("Jack");
            Iterator<HelloProto.HelloResponse> helloResponseIterator = stub.c2ss(builder.build());
            while (helloResponseIterator.hasNext()) {
                HelloProto.HelloResponse helloResponse = helloResponseIterator.next();
                System.out.println("服务端响应：" + helloResponse.getResult());
            }
        } finally {
            channel.shutdown();
        }
    }
}
