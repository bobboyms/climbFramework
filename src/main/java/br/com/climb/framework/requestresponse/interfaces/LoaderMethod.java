package br.com.climb.framework.requestresponse.interfaces;

import br.com.climb.commons.execptions.NotFoundException;
import br.com.climb.commons.reqrespmodel.Request;
import br.com.climb.framework.requestresponse.model.Capsule;

import java.io.IOException;

public interface LoaderMethod {
    public Capsule getMethodForCall(Request request) throws NotFoundException, IOException;
}
