package ai.zgc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;

/**
 * 双向流式RPC的客户端
 */
public class GrpcClient6 {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080).usePlaintext().build();
        try {
            HelloServiceGrpc.HelloServiceStub helloServiceStub = HelloServiceGrpc.newStub(channel);

            StreamObserver<HelloProto.HelloRequest> helloRequestStreamObserver = helloServiceStub.cs2ss(new StreamObserver<HelloProto.HelloResponse>() {
                @Override
                public void onNext(HelloProto.HelloResponse helloResponse) {
                    System.out.println("响应的结果 " + helloResponse.getResult());
                }

                @Override
                public void onError(Throwable throwable) {

                }

                @Override
                public void onCompleted() {
                    System.out.println("响应全部结束...");
                }
            });

            for (int i = 0; i < 10; i++) {
                HelloProto.HelloRequest helloRequest = HelloProto.HelloRequest.newBuilder().setName("Jerry " + i).build();
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
