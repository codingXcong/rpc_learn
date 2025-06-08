package ai.zgc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;

/**
 * 客户端流式RPC的客户端
 */
public class GrpcClient5 {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080).usePlaintext().build();
        try {
            HelloServiceGrpc.HelloServiceStub helloServiceStub = HelloServiceGrpc.newStub(channel);

            StreamObserver<HelloProto.HelloRequest> helloRequestStreamObserver = helloServiceStub.cs2s(new StreamObserver<HelloProto.HelloResponse>() {
                @Override
                public void onNext(HelloProto.HelloResponse helloResponse) {
                    // 监控响应
                    System.out.println("服务端 响应 数据内容为 " + helloResponse.getResult());
                }

                @Override
                public void onError(Throwable throwable) {

                }

                @Override
                public void onCompleted() {
                    System.out.println("服务端响应结束 ... ");
                }
            });

            //客户端 发送数据 到服务端  多条数据 ，不定时...
            for (int i = 0; i < 10; i++) {
                HelloProto.HelloRequest helloRequest = HelloProto.HelloRequest.newBuilder()
                        .setName("Jack " + i).build();
                helloRequestStreamObserver.onNext(helloRequest);
            }
            helloRequestStreamObserver.onCompleted();

            channel.awaitTermination(20, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            channel.shutdown();
        }
    }
}
