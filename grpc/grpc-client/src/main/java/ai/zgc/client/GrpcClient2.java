package ai.zgc.client;

import com.google.protobuf.ProtocolStringList;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * 一元RPC的客户端
 */
public class GrpcClient2 {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080).usePlaintext().build();
        try {
            HelloServiceGrpc.HelloServiceBlockingStub helloServer = HelloServiceGrpc.newBlockingStub(channel);
            HelloProto.HelloListRequest.Builder requestBuilder = HelloProto.HelloListRequest.newBuilder();
            requestBuilder.addName("Jack");
            requestBuilder.addName("Bob");
            requestBuilder.addName("Tom");

            HelloProto.HelloListResponse helloListResponse = helloServer.helloList(requestBuilder.build());
            ProtocolStringList resultList = helloListResponse.getResultList();
            resultList.forEach(System.out::println);
        } finally {
            channel.shutdownNow();
        }
    }
}
