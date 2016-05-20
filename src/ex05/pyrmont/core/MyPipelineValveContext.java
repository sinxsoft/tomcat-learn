package ex05.pyrmont.core;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.catalina.Pipeline;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;
import org.apache.catalina.ValveContext;

class MyPipelineValveContext implements ValveContext {

  protected int stage = 0;

  protected final Pipeline _pipeline;

  public MyPipelineValveContext(Pipeline pipeline) {
    this._pipeline = pipeline;
  }

  public String getInfo() {
    return null;
  }

  public void invokeNext(Request request, Response response) throws IOException, ServletException {
    int subscript = stage;
    stage = stage + 1;
    // Invoke the requested Valve for the current request thread
    Valve[] valves = _pipeline.getValves();
    Valve basic = _pipeline.getBasic();
    if (subscript < valves.length) {
      valves[subscript].invoke(request, response, this);
    } else if ((subscript == valves.length) && (basic != null)) {
      basic.invoke(request, response, this);
    } else {
      throw new ServletException("No valve");
    }
  }
}
