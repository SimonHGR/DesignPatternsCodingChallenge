package builder;

import java.util.ArrayList;
import java.util.List;

public class MyClass {
  private StringBuilder a;
  private List<String> b;
  private int c;

  public StringBuilder getA() {
    return new StringBuilder(a);
  }

  public List<String> getB() {
    return List.copyOf(b);
  }

  public int getC() {
    return c;
  }

  private MyClass() {
  }

  public static class MyBuilder {
    private MyClass self = new MyClass();
    private boolean used = false;

    private MyBuilder() {
    }

    public MyBuilder a(StringBuilder sb) {
      if (used) renew();
      self.a = sb;
      return this;
    }

    public MyBuilder b(List<String> ls) {
      if (used) renew();
      self.b = new ArrayList<>(ls);
      return this;
    }

    public MyBuilder c(int c) {
      if (used) renew();
      self.c = c;
      return this;
    }

    public MyClass build() {
      // validation goes here
      if (used) renew();
      used = true;
      return self;
    }

    private void renew() {
      MyClass next = new MyClass();
      next.a = new StringBuilder(self.a);
      next.b = new ArrayList<>(self.b);
      next.c = self.c;
      self = next;
      used = false;
    }
  }

  public static MyBuilder builder() {
    return new MyBuilder();
  }
}
