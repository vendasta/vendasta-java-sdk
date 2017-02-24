package com.vendasta.socialposts.v1;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.1.2)",
    comments = "Source: social-posts.proto")
public class SocialPostsGrpc {

  private SocialPostsGrpc() {}

  public static final String SERVICE_NAME = "socialposts.v1.SocialPosts";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.vendasta.socialposts.v1.SocialPostsProtos.ListSocialPostRequest,
      com.vendasta.socialposts.v1.SocialPostsProtos.ListSocialPostResponse> METHOD_LIST =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "socialposts.v1.SocialPosts", "List"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.vendasta.socialposts.v1.SocialPostsProtos.ListSocialPostRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.vendasta.socialposts.v1.SocialPostsProtos.ListSocialPostResponse.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SocialPostsStub newStub(io.grpc.Channel channel) {
    return new SocialPostsStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SocialPostsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SocialPostsBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static SocialPostsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SocialPostsFutureStub(channel);
  }

  /**
   */
  public static abstract class SocialPostsImplBase implements io.grpc.BindableService {

    /**
     */
    public void list(com.vendasta.socialposts.v1.SocialPostsProtos.ListSocialPostRequest request,
        io.grpc.stub.StreamObserver<com.vendasta.socialposts.v1.SocialPostsProtos.ListSocialPostResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_LIST, responseObserver);
    }

    @java.lang.Override 
    public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_LIST,
            asyncUnaryCall(
              new MethodHandlers<
                com.vendasta.socialposts.v1.SocialPostsProtos.ListSocialPostRequest,
                com.vendasta.socialposts.v1.SocialPostsProtos.ListSocialPostResponse>(
                  this, METHODID_LIST)))
          .build();
    }
  }

  /**
   */
  public static final class SocialPostsStub extends io.grpc.stub.AbstractStub<SocialPostsStub> {
    private SocialPostsStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SocialPostsStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SocialPostsStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SocialPostsStub(channel, callOptions);
    }

    /**
     */
    public void list(com.vendasta.socialposts.v1.SocialPostsProtos.ListSocialPostRequest request,
        io.grpc.stub.StreamObserver<com.vendasta.socialposts.v1.SocialPostsProtos.ListSocialPostResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_LIST, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SocialPostsBlockingStub extends io.grpc.stub.AbstractStub<SocialPostsBlockingStub> {
    private SocialPostsBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SocialPostsBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SocialPostsBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SocialPostsBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.vendasta.socialposts.v1.SocialPostsProtos.ListSocialPostResponse list(com.vendasta.socialposts.v1.SocialPostsProtos.ListSocialPostRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_LIST, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SocialPostsFutureStub extends io.grpc.stub.AbstractStub<SocialPostsFutureStub> {
    private SocialPostsFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SocialPostsFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SocialPostsFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SocialPostsFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.vendasta.socialposts.v1.SocialPostsProtos.ListSocialPostResponse> list(
        com.vendasta.socialposts.v1.SocialPostsProtos.ListSocialPostRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_LIST, getCallOptions()), request);
    }
  }

  private static final int METHODID_LIST = 0;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SocialPostsImplBase serviceImpl;
    private final int methodId;

    public MethodHandlers(SocialPostsImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LIST:
          serviceImpl.list((com.vendasta.socialposts.v1.SocialPostsProtos.ListSocialPostRequest) request,
              (io.grpc.stub.StreamObserver<com.vendasta.socialposts.v1.SocialPostsProtos.ListSocialPostResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class SocialPostsDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.vendasta.socialposts.v1.SocialPostsProtos.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SocialPostsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SocialPostsDescriptorSupplier())
              .addMethod(METHOD_LIST)
              .build();
        }
      }
    }
    return result;
  }
}
