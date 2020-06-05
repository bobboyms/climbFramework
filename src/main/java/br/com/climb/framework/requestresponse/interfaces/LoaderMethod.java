package br.com.climb.framework.requestresponse.interfaces;

import br.com.climb.framework.execptions.NotFoundException;
import br.com.climb.framework.requestresponse.model.Capsule;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface LoaderMethod {
    Capsule getMethodForCall(HttpServletRequest request) throws NotFoundException, IOException;
}
