package ai.zgc.server;

import com.google.protobuf.ProtocolStringList;
import io.grpc.stub.StreamObserver;

public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

    /**
     * grpc 的返回参数是通过 responseObserver 写回客户端的
     * @param request
     * @param responseObserver
     */
    @Override
    public void hello(HelloProto.HelloRequest request, StreamObserver<HelloProto.HelloResponse> responseObserver) {
        //1.接受client的请求参数
        String name = request.getName();
        //2.业务处理
        System.out.println("Hello " + name);
        //3.封装响应
        //3.1 创建相应对象的构造者
        HelloProto.HelloResponse.Builder builder = HelloProto.HelloResponse.newBuilder();
        //3.2 填充数据
        builder.setResult("Hello " + name);
        //3.3 封装响应（响应的消息通过网络回传给client）
        responseObserver.onNext(builder.build());
        // 通知client响应已经结束
        responseObserver.onCompleted();
    }

    /**
     * 一元RPC
     * @param request
     * @param responseObserver
     */
    @Override
    public void helloList(HelloProto.HelloListRequest request, StreamObserver<HelloProto.HelloListResponse> responseObserver) {
        ProtocolStringList nameList = request.getNameList();
        for (String s : nameList) {
            System.out.println("Hello " + s);
        }

        // 构建响应对象
        HelloProto.HelloListResponse.Builder builder = HelloProto.HelloListResponse.newBuilder();
        for (String s : nameList) {
            builder.addResult("你好呀， " + s);
        }
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    /**
     * 服务端流式RPC
     * @param request
     * @param responseObserver
     */
    @Override
    public void c2ss(HelloProto.HelloRequest request, StreamObserver<HelloProto.HelloResponse> responseObserver) {
        //1.接受client的请求参数
        String name = request.getName();
        //2.业务处理
        System.out.println("Hello " + name);
        //3 根据业务处理的结果，提供响应
        for (int i = 0; i < 9; i++) {
            HelloProto.HelloResponse.Builder builder = HelloProto.HelloResponse.newBuilder();
            builder.setResult("no." + i + " Hello " + name);
            responseObserver.onNext(builder.build());
        }
        responseObserver.onCompleted();
    }

    /**
     * 客户端流式RPC
     * @param responseObserver
     * @return
     */
    @Override
    public StreamObserver<HelloProto.HelloRequest> cs2s(StreamObserver<HelloProto.HelloResponse> responseObserver) {
        return new StreamObserver<HelloProto.HelloRequest>() {
            @Override
            public void onNext(HelloProto.HelloRequest helloRequest) {
                System.out.println("接受到了client发送一条消息 " + helloRequest.getName());
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                System.out.println("client的所有消息 都发送到了 服务端 ....");
                HelloProto.HelloResponse helloResponse = HelloProto.HelloResponse.newBuilder()
                        .setResult("Hello client stream").build();
                responseObserver.onNext(helloResponse);
                responseObserver.onCompleted();
            }
        };
    }

    /**
     * 双向流式RPC
     * @param responseObserver
     * @return
     */
    @Override
    public StreamObserver<HelloProto.HelloRequest> cs2ss(StreamObserver<HelloProto.HelloResponse> responseObserver) {
        return new StreamObserver<HelloProto.HelloRequest>() {
            @Override
            public void onNext(HelloProto.HelloRequest helloRequest) {
                System.out.println("收到client 提交的消息：" + helloRequest.getName());
                responseObserver.onNext(HelloProto.HelloResponse.newBuilder().setResult("Hello" + helloRequest.getName()).build());
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                System.out.println("接收了客户端所有的消息");
                responseObserver.onCompleted();
            }
        };
    }
}
