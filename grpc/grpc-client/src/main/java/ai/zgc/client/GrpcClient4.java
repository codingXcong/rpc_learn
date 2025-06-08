package ai.zgc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;

/**
 * 异步方式 服务端流式RPC客户端
 */
public class GrpcClient4 {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080).usePlaintext().build();
        try {
            HelloServiceGrpc.HelloServiceStub helloServiceStub = HelloServiceGrpc.newStub(channel);

            HelloProto.HelloRequest.Builder builder = HelloProto.HelloRequest.newBuilder();
            builder.setName("Jack");
            HelloProto.HelloRequest helloRequest = builder.build();

            // 异步监听
            helloServiceStub.c2ss(helloRequest, new StreamObserver<HelloProto.HelloResponse>() {

                @Override
                public void onNext(HelloProto.HelloResponse helloResponse) {
                    //服务端 响应了 一个消息后，需要立即处理的话。把代码写在这个方法中。
                    System.out.println("服务端每一次响应的信息 " + helloResponse.getResult());
                }

                @Override
                public void onError(Throwable throwable) {

                }

                @Override
                public void onCompleted() {
                    //需要把服务端 响应的所有数据 拿到后，在进行业务处理。
                    System.out.println("服务端响应结束 后续可以根据需要 在这里统一处理服务端响应的所有内容");
                }
            });

            channel.awaitTermination(20, TimeUnit.SECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            channel.shutdown();
        }
    }

}
