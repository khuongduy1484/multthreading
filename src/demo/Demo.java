package demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo {
  public static void main(String[] args) {
    ExecutorService executorService = Executors.newCachedThreadPool();
    for (int i = 0; i < 100; i++) {
      executorService.submit(new Person(i) {
        @Override
        public void run() {
          super.run();
        }
      });
    }
  }
}
