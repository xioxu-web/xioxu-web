package com.orc.rpc.server;


import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.netty.buffer.ByteBuf;
import java.util.concurrent.*;

/**
 * @author xiaoxu123
 */
public class NettyRpcServer extends RpcServer {

    private static Logger logger=LoggerFactory.getLogger(NettyRpcServer.class);

    private Channel channel;

    private static final ExecutorService pool = new ThreadPoolExecutor(4, 8,
            200, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000),
            new ThreadFactoryBuilder().setNameFormat("rpcServer-%d").build());

    public NettyRpcServer(int port, String protocol, RequestHandler requestHandler) {
        super( port, protocol, requestHandler );
    }

    @Override
    public void start() throws InterruptedException {
        //配置服务器
        NioEventLoopGroup masterGroup = new NioEventLoopGroup( 1 );
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group( masterGroup, workerGroup )
                    .channel( NioServerSocketChannel.class )
                    .option( ChannelOption.SO_BACKLOG, 100 )
                    .handler( new LoggingHandler( LogLevel.INFO ) ).childHandler( new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addLast( new ChannelRequestHandler() );
                }
            } );
            // 启动服务
            ChannelFuture future = server.bind( port ).sync();
            logger.debug( "Server started successfully." );
            channel = future.channel();
            //等待服务通道关闭
            future.channel().closeFuture().sync();
        }catch (Exception e){
           e.printStackTrace();
           logger.error("start netty sever failed,msg:{}", e.getMessage());
        }finally {
            //释放线程组
            masterGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    @Override
    public void stop() {
     this.channel.close();
    }

    private class ChannelRequestHandler extends ChannelInboundHandlerAdapter{
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            logger.debug( "Channel active :{}", ctx );
        }
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            pool.submit(()->{
                try {
                logger.debug("the server receives message :{}", msg);
                ByteBuf byteBuf = (ByteBuf) msg;
                byte[] reqData = new byte[byteBuf.readableBytes()];
                byteBuf.readBytes( reqData );
                //手动回收
                ReferenceCountUtil.release( byteBuf );
                byte[] respData = new byte[0];
                    respData = requestHandler.handleRequest(reqData);
                ByteBuf respBuf = Unpooled.buffer(respData.length);
                respBuf.writeBytes(respData);
                logger.debug("Send response:{}", respBuf);
                ctx.writeAndFlush(respBuf);
                } catch (Exception e) {
                    logger.error( "server read exception...", e );
                }
            });
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            // Close the connection when an exception is raised.
            cause.printStackTrace();
            logger.error("Exception occurred:{}", cause.getMessage());
            ctx.close();
        }

    }
}
