package ex05.pyrmont.startup;

import org.apache.catalina.Loader;
import org.apache.catalina.Pipeline;
import org.apache.catalina.Valve;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.http.HttpConnector;

import ex05.pyrmont.core.SimpleLoader;
import ex05.pyrmont.core.SimpleWrapper;
import ex05.pyrmont.valves.ClientIPLoggerValve;
import ex05.pyrmont.valves.HeaderLoggerValve;

// Connector直接将请求放去Servlet封装Wrapper里面处理
public final class Bootstrap1 {
  public static void main(String[] args) {

    /*
     * call by using http://localhost:8080/ModernServlet, but could be invoked by any name
     */

    HttpConnector connector = new HttpConnector();
    // define a servlet封装
    Wrapper wrapper = new SimpleWrapper();
    // 给servlet封装设置一个servlet类
    wrapper.setServletClass("ModernServlet");
    // 类加载器
    Loader loader = new SimpleLoader();
    // 该servlet类上面的的过滤器（阀）
    Valve valve1 = new HeaderLoggerValve();
    Valve valve2 = new ClientIPLoggerValve();
    // 给servlet封装设置一个加载器
    wrapper.setLoader(loader);
    // 该servlet封装器同时又是一个pipeline，是一个filterchain封装，里面可以放入多个filter封装
    ((Pipeline) wrapper).addValve(valve1);
    ((Pipeline) wrapper).addValve(valve2);
    // 连接器设置容器，注意，此处wrapper具备多重功能
    connector.setContainer(wrapper);

    try {
      connector.initialize();
      connector.start();

      // make the application wait until we press a key.
      System.in.read();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
