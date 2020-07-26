package br.com.climb.framework.tcpserver;

import org.apache.mina.core.session.IoSession;

public interface EntryPoint {
    void exec(IoSession session, Object message);
}
