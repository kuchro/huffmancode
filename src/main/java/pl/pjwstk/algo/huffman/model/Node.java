package pl.pjwstk.algo.huffman.model;


public class Node {
    private Character character;
    private Integer freq;
    private Node left = null, right = null;

    public Node(Character ch, Integer freq)
    {
        this.character = ch;
        this.freq = freq;
    }

    public Node(Character ch, Integer freq, Node left, Node right)
    {
        this.character = ch;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character ch) {
        this.character = ch;
    }

    public Integer getFreq() {
        return freq;
    }

    public void setFreq(Integer freq) {
        this.freq = freq;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}
