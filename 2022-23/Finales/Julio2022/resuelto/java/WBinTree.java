/* ------------------------------------------------------------------------------
   -- Student's name: Diego Cuesta Garcia
   -- Student's group: A
   -- Identity number (DNI if Spanish/passport if Erasmus): 09077832P
   --
   -- Data Structures. Grado en Informática. UMA.
   -------------------------------------------------------------------------------
*/

package exercises;

public class WBinTree<T> {

  private static class Node<E> {
    private E item;
    private int weight;
    private Node<E> left, right;

    Node(E x, int w, Node<E> lt, Node<E> rt) {
      item = x;
      weight = w;
      left = lt;
      right = rt;
    }
  }

  private Node<T> root;

  public WBinTree() {
    root = null;
  }

  // -- ESCRIBE TU SOLUCIÓN DEBAJO ----------------------------------------------
  // -- WRITE YOUR SOLUTION BELOW  ----------------------------------------------
  // -- EXERCISE 4

  public boolean isWeightBalanced() {
    return isWeightBalanced(root);
  }

  public int numNodos(Node<T> node) {
    return node == null ? 0: node.weight;
  }
  public boolean isWeightBalanced(Node<T> node) {
    if (node == null) return true;
    else return numNodos(node.left)>= numNodos(node.right)
              && Math.abs(numNodos(node.left)-numNodos(node.right))<= 1 &&
              isWeightBalanced(node.left) && isWeightBalanced(node.right);
  }

  public void insert(T x) {
    root = insert(root, x);
  }

  public Node<T> insert(Node<T> node, T x) {
    if (node == null)
      return new Node<>(x, 1, null, null);
    else {
      if (numNodos(node.left) > numNodos(node.right))
        node.right = insert(node.right, x);
      else node.left = insert(node.left, x);
      node.weight++;
    }
    return node;
  }


  // -- DO NOT MODIFY CODE BELOW -----------------------------------------------------------
  // -- NO MODIFIQUES DEBAJO ---------------------------------------------------------------

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    toStringRec(root, sb);
    return sb.toString();
  }

  private static <T> void toStringRec(Node<T> node, StringBuffer sb) {
    if (node == null) {
      sb.append("null");
    } else {
      sb.append(String.format("Node(%d, %s, ", node.weight, node.item));
      toStringRec(node.left, sb);
      sb.append(", ");
      toStringRec(node.right, sb);
      sb.append(")");
    }
  }
}
