package pl.pjwstk.algo.huffman.collection;

import pl.pjwstk.algo.huffman.model.Node;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class NodePriorityQueue {
    private static final int INIT_CAPACITY = 10;
    private Node[] queue;
    private int size;
    public int size() {
        return size;
    }
    public Node peek() {
        return  queue[0];
    }

    private final Comparator<? super Node> comparator;

    public NodePriorityQueue(Comparator<? super Node> comparator) {
        this(INIT_CAPACITY, comparator);
    }

    public NodePriorityQueue(int initialCapacity,
                             Comparator comparator) {
        this.queue = new Node[initialCapacity];
        this.comparator = comparator;
    }

    private void grow(int minCapacity) {
        int oldCapacity = queue.length;
        queue = Arrays.copyOf(queue, Math.max(oldCapacity, minCapacity - oldCapacity) +
                (oldCapacity < 64 ? oldCapacity + 2 : oldCapacity >> 1));
    }

    public boolean add(Node node) {
        if (Objects.isNull(node))
            throw new NullPointerException();
        int i = size;
        if (i >= queue.length)
            grow(i + 1);
        insertToQueue(i, node);
        size = i + 1;
        return true;
    }

    private void insertToQueue(int k, Node node) {
        insertNodeAtPosUp(k, node, queue, comparator);
    }

    private static void insertNodeAtPosUp(
            int k, Node x, Node[] nodesQueue, Comparator cmp) {
        while (k > 0) {
            int parent = (k - 1) >>> 1;
            Node e = nodesQueue[parent];
            if (cmp.compare(x,e) >= 0)
                break;
            nodesQueue[k] = e;
            k = parent;
        }
        nodesQueue[k] = x;
    }

    public Node poll() {
        final Node[] nodes;
        final Node result;

        if ((result = ((nodes = queue)[0])) != null) {
            final int n;
            final Node x = nodes[(n = --size)];
            nodes[n] = null;
            if (n > 0) {
                insertNodeAtPosDown(0, x, nodes, n, comparator);
            }
        }
        return result;
    }

    private static void insertNodeAtPosDown(
            int k, Node node, Node[] nodes, int n, Comparator cmp) {
        int half = n >>> 1;
        while (k < half) {
            int child = (k << 1) + 1;
            Node c = nodes[child];
            int right = child + 1;
            if (right < n && cmp.compare(c, nodes[right]) > 0)
                c = nodes[child = right];
            if (cmp.compare(node, c) <= 0)
                break;
            nodes[k] = c;
            k = child;
        }
        nodes[k] = node;
    }

}
