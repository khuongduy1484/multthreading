package demo;

import java.util.concurrent.Semaphore;

public class BookStore {
  private static final int MAX_AVAILABLE = 10;
  private final Semaphore available = new Semaphore(MAX_AVAILABLE, true);
  public static BookStore bookStore;
  protected Book[] books = new Book[MAX_AVAILABLE];
  protected boolean[] booked = new boolean[MAX_AVAILABLE];

  public Book borrowBook() throws InterruptedException {
    available.acquire();
    return getNextAvailableItem();
  }

  protected Book getNextAvailableItem() {
    for (int i = 0; i < MAX_AVAILABLE; ++i) {
      if (!booked[i]) {
        booked[i] = true;
        return books[i];
      }
    }
    return null;
  }

  public boolean returnBook(Book book, int id) {
    boolean returnSucceed = markAsUnused(book);
    if (returnSucceed) {
      System.out.println("Person " + id + " returns book nicely");
      available.release();
      book = null;
    }
    return returnSucceed;
  }

  protected synchronized boolean markAsUnused(Book item) {
    for (int i = 0; i < MAX_AVAILABLE; ++i) {
      if (item == books[i]) {
        if (booked[i]) {
          booked[i] = false;
          return true;
        }
        else
          return false;
      }
    }
    return false;
  }

  public static BookStore getBookStore() {
    if (bookStore == null) {
      bookStore = new BookStore();
    }
    return bookStore;
  }


  private BookStore() {
    for (int i = 0; i < MAX_AVAILABLE; ++i) {
      books[i] = new Book();
    }
  }


}

