package ex06.pyrmont.core;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;

/**
 * 观察者模式的典型应用
 * 
 * 将该类实例放入观察者池子
 * 
 * 每当池子管理者要fire该事件，
 * 
 * 则会构建一个LifecycleEvent对象，
 * 
 * 通知池子里面的每一个LifeycleListener。
 * 
 * @author vagrant
 *
 */
public class SimpleContextLifecycleListener implements LifecycleListener {

  public void lifecycleEvent(LifecycleEvent event) {
    Lifecycle lifecycle = event.getLifecycle();
    System.out.println("SimpleContextLifecycleListener's event " + event.getType().toString());
    if (Lifecycle.START_EVENT.equals(event.getType())) {
      System.out.println("Starting context.");
    } else if (Lifecycle.STOP_EVENT.equals(event.getType())) {
      System.out.println("Stopping context.");
    }
  }
}
