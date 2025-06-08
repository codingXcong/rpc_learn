package ai.zgc.grpcbootserver;

import ai.zgc.HelloProto;
import ai.zgc.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

    @Override
    public void hello(HelloProto.HelloRequest request, StreamObserver<HelloProto.HelloResponse> responseObserver) {
        String name = request.getName();

        System.out.println("Hello " + name);

        HelloProto.HelloResponse.Builder builder = HelloProto.HelloResponse.newBuilder().setResult("hello " + name);
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
