/*
  Student's name: ??????????
  Identity number (DNI if Spanish/passport if Erasmus): ???????????
 */

package exercises;

import java.util.Random;
import java.util.StringJoiner;

public class LinkedSeq<T> {
  private static class Node<E> {
    E elem;
    Node<E> next;

    public Node(E elem, Node<E> next) {
      this.elem = elem;
      this.next = next;
    }
  }

  private Node<T> first;

  public LinkedSeq() {
    first = null;
  }

  private LinkedSeq(Node<T> node) {
    first = node;
  }

  public static LinkedSeq<Integer> fromInt(int n) {
    return new LinkedSeq<>(fromInt(n, null));
  }

  private static Node<Integer> fromInt(int n, Node<Integer> node) {
    return (n < 10 ? new Node<>(n, node)
            : fromInt(n / 10, new Node<>(n % 10, node)));
  }

  @Override
  public boolean equals(Object that) {
    if (this == that)
      return true;
    if (that == null)
      return false;
    if (!(that instanceof LinkedSeq<?>))
      return false;
    else
      return equals(this.first, ((LinkedSeq<?>) that).first);
  }

  private static boolean equals(Node<?> node1, Node<?> node2) {
    if (node1 == null)
      return node2 == null;
    else if (node2 == null)
      return false;
    else
      return node1.elem.equals(node2.elem) && equals(node1.next, node2.next);
  }

  @Override
  public String toString() {
    StringJoiner joiner = new StringJoiner(", ", "LinkedSeq(", ")");
    for (Node<T> node = first; node != null; node = node.next)
      joiner.add(node.elem.toString());
    return joiner.toString();
  }

  /* DO NOT WRITE ANY CODE ABOVE THIS LINE */
  /* WRITE YOUR SOLUTION BELOW             */

  public static void addSingleDigit(int d, LinkedSeq<Integer> linkedSeq) {
    int acarreo = addSingleDigit(d, linkedSeq.first);
    if (acarreo != 0)
      linkedSeq.first = new Node(acarreo,linkedSeq.first);
  }
  public static int addSingleDigit(int d, Node<Integer> node) {
    if (node == null)
      return d;
    else {
      int acarreo = addSingleDigit(d,node.next);
      int s =  acarreo+node.elem;
      node.elem = s % 10;
      return s / 10;
    }
  }
}

