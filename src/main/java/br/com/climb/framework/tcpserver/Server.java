package br.com.climb.framework.tcpserver;

import br.com.climb.cdi.ManagerContext;
import br.com.climb.commons.annotations.RestController;
import br.com.climb.commons.configuration.ConfigFile;
import br.com.climb.commons.generictcpclient.TcpClient;
import br.com.climb.commons.model.DiscoveryRequest;
import br.com.climb.commons.model.DiscoveryResponse;
import br.com.climb.commons.model.ReceiveMessage;
import br.com.climb.framework.ClimbApplication;
import br.com.climb.framework.clientdiscovery.ClientHandler;
import br.com.climb.framework.clientdiscovery.DiscoveryClient;
import br.com.climb.framework.messagesclient.HandlerMessage;
import br.com.climb.framework.messagesclient.annotations.MessageController;
import br.com.climb.framework.messagesclient.tcpclient.receive.ReceiveMessageClient;
import br.com.climb.framework.requestresponse.LoaderClassController;
import br.com.climb.framework.requestresponse.interfaces.Storage;
import br.com.climb.rpc.annotation.RpcController;
import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

import static br.com.climb.commons.utils.ReflectionUtils.getAnnotedClass;
import static br.com.climb.framework.messagesclient.Methods.MESSAGE_CONTROLLERS;

public class Server implements TcpServer {

    private static Logger logger = LoggerFactory.getLogger(Server.class);

    public final ConfigFile configFile;

    public Server(ConfigFile configFile) {
        this.configFile = configFile;
    }

    private void initMessageThread() {

        new Thread(()->{

            while (true) {

                try {

                    MESSAGE_CONTROLLERS.entrySet().forEach(entry -> {

                        final TcpClient discoveryClient = new ReceiveMessageClient(new ClientHandler(), "127.0.0.1", 3254);
                        discoveryClient.sendRequest(entry.getKey());
                        ReceiveMessage response = (ReceiveMessage) discoveryClient.getResponse();

                        if (response.getMessages().size() > 0) {

                            try (final ManagerContext context = ClimbApplication.containerInitializer.createManager()) {

                                final Object instance = context.generateInstance(entry.getValue());

                                response.getMessages().forEach(sendMessage -> {
                                    ((HandlerMessage) instance).messageReceived(sendMessage.getMessage());
                                });

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        discoveryClient.closeConnection();

                    });
                } catch (RuntimeIoException e) {
                    System.out.println("It was not possible to connect to the messaging server: " + configFile.getMessageIp() + "/" + configFile.getMessagePort());

                    try {
                        Thread.sleep(6000);
                    } catch (InterruptedException ex) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    logger.error("Error: {}", e);
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }).start();

    }

    private void initDiscoveryThread(DiscoveryRequest discoveryRequest) {

        new Thread(()->{

            while (true) {

                try {
                    final TcpClient discoveryClient = new DiscoveryClient(new ClientHandler(), configFile.getGatewayIp(), new Integer(configFile.getGatewayPort()));
                    discoveryClient.sendRequest(discoveryRequest);
                    DiscoveryResponse discoveryResponse = (DiscoveryResponse) discoveryClient.getResponse();
                    discoveryClient.closeConnection();
                } catch (RuntimeIoException e) {
                    System.out.println("It was not possible to connect to the api gateway: " + configFile.getGatewayIp() + "/" + configFile.getGatewayPort());
                } catch (Exception e) {
                    logger.error("Error: {}", e);
                }

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }).start();
    }

    @Override
    public void start() throws Exception {

        final Storage storage = new LoaderClassController();

        storage.storageRpcControllers(getAnnotedClass(RpcController.class, configFile.getPackage()));
        storage.storageMessageControllers(getAnnotedClass(MessageController.class, configFile.getPackage()));

        final DiscoveryRequest discoveryRequest = storage
                .storageRestControllers(getAnnotedClass(RestController.class, configFile.getPackage()))
                .generateDiscoveryRequest(configFile.getLocalIp(),configFile.getLocalPort());

        initMessageThread();
        initDiscoveryThread(discoveryRequest);

        IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast( "logger1", new LoggingFilter() );
        acceptor.getFilterChain().addLast( "codec1", new ProtocolCodecFilter(
                new ObjectSerializationCodecFactory()));
        acceptor.setHandler( new ServerHandler() );
        acceptor.getSessionConfig().setReadBufferSize( 2048 );
        acceptor.bind(new InetSocketAddress(new Integer(configFile.getLocalPort())));
    }
}
