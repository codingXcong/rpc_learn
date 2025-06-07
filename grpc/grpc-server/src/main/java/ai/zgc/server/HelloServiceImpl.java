package ai.zgc.server;

import ai.zgc.HelloProto;
import ai.zgc.HelloServiceGrpc;
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
}
