package ex13.pyrmont.startup;

//explain Host
import ex13.pyrmont.core.SimpleContextConfig;
import org.apache.catalina.Connector;
import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Loader;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.core.StandardWrapper;
import org.apache.catalina.loader.WebappLoader;


public final class Bootstrap1 {
  public static void main(String[] args) {
    //invoke: http://localhost:8080/app1/Primitive or http://localhost:8080/app1/Modern
    System.setProperty("catalina.base", System.getProperty("user.dir"));
    Connector connector = new HttpConnector();

    Wrapper wrapper1 = new StandardWrapper();
    wrapper1.setName("Primitive");
    wrapper1.setServletClass("PrimitiveServlet");
    Wrapper wrapper2 = new StandardWrapper();
    wrapper2.setName("Modern");
    wrapper2.setServletClass("ModernServlet");

    Context context = new StandardContext();
    // StandardContext's start method adds a default mapper
    context.setPath("/app1");
    context.setDocBase("app1");

    context.addChild(wrapper1);
    context.addChild(wrapper2);

    LifecycleListener listener = new SimpleContextConfig();
    ((Lifecycle) context).addLifecycleListener(listener);
    
    
    
    wrapper1 = new StandardWrapper();
    wrapper1.setName("Primitive");
    wrapper1.setServletClass("PrimitiveServlet");
    wrapper2 = new StandardWrapper();
    wrapper2.setName("Modern");
    wrapper2.setServletClass("ModernServlet");
    
    Context context2 = new StandardContext();
    // StandardContext's start method adds a default mapper
    context2.setPath("/app2");
    context2.setDocBase("app2");

    context2.addChild(wrapper1);
    context2.addChild(wrapper2);

    LifecycleListener listener2 = new SimpleContextConfig();
    ((Lifecycle) context2).addLifecycleListener(listener2);
    

    Host host = new StandardHost();
    host.addChild(context);
    
    host.addChild(context2);
    
    host.setName("localhost");
    host.setAppBase("webapps");

    Loader loader = new WebappLoader();
    context.setLoader(loader);
    // context.addServletMapping(pattern, name);
    context.addServletMapping("/Primitive", "Primitive");
    context.addServletMapping("/Modern", "Modern");
    
    Loader loader2 = new WebappLoader();
    context2.setLoader(loader2);
    
    context2.addServletMapping("/Primitive", "Primitive");
    context2.addServletMapping("/Modern", "Modern");

    connector.setContainer(host);
    try {
      connector.initialize();
      ((Lifecycle) connector).start();
      ((Lifecycle) host).start();
      System.out.println("started......ok......");
      // make the application wait until we press a key.
      System.in.read();
      ((Lifecycle) host).stop();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}