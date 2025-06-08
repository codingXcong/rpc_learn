package ai.zgc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GprcClient1 {
    public static void main(String[] args) {
        //1 创建通信的管道
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8080).usePlaintext().build();
        //2 获得代理对象 stub
        try {
            HelloServiceGrpc.HelloServiceBlockingStub helloService = HelloServiceGrpc.newBlockingStub(managedChannel);
            //3. 完成RPC调用
            //3.1 准备参数
            HelloProto.HelloRequest.Builder builder = HelloProto.HelloRequest.newBuilder();
            builder.setName("grpc man");
            HelloProto.HelloRequest helloRequest = builder.build();
            //3.1 进行功能rpc调用，获取相应的内容
            HelloProto.HelloResponse helloResponse = helloService.hello(helloRequest);
            String result = helloResponse.getResult();
            System.out.println("result = " + result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            managedChannel.shutdown();
        }
    }
}
