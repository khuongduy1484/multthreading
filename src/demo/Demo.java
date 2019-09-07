package demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo {
  public static void main(String[] args) {
    ExecutorService executorService = Executors.newCachedThreadPool();
    for (int i = 0; i < 10; i++) {
      int personId = i;
      executorService.submit(new Runnable() {
        @Override
        public void run() {
          Person person = new Person(personId);
          person.borrowBook();
        }
      });
    }
  }
}
