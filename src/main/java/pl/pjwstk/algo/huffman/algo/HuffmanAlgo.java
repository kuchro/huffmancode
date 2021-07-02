package pl.pjwstk.algo.huffman.algo;

import pl.pjwstk.algo.huffman.collection.NodePriorityQueue;
import pl.pjwstk.algo.huffman.helper.FileHelper;
import pl.pjwstk.algo.huffman.model.Node;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HuffmanAlgo {
    private static final StringBuilder decodedStr = new StringBuilder();

    private static Node rootElement;
    private static StringBuilder encodedString;

    public static boolean isLeaf(Node root) {
        return root.getLeft() == null && root.getRight() == null;
    }

    public static void createHuffmanTree(String text) throws IOException {
        if (text == null || text.length() == 0) {
            throw new RuntimeException("The input text cannot be null or empty.");
        }

        Map<Character, Integer> freq = getCharacterIntegerMap(text);

        NodePriorityQueue pq = new NodePriorityQueue(Comparator.comparingInt(Node::getFreq));

        createNodeAndAddToPrioQueue(freq, pq);

        while (pq.size() != 1) {

            Node leftNode = pq.poll();
            Node rightNode = pq.poll();

            int sum = leftNode.getFreq() + rightNode.getFreq();
            pq.add(new Node(null, sum, leftNode, rightNode));
        }

        rootElement = pq.peek();

        Map<Character, String> huffmanCode = new HashMap<>();
        encode(rootElement, "", huffmanCode);

        encodedString = new StringBuilder();
        for (char c : text.toCharArray()) {
            encodedString.append(huffmanCode.get(c));
        }
    }

    public static void encodedStringSaveToFile() throws IOException {
        System.out.println("\nEncoded String save to file");
        FileHelper.writeToFile("output", encodedString);
    }

    public static void decodedContentSaveToFile() throws IOException {
        System.out.println("\nRead previously encoded content\n");
        String fromFile = FileHelper.textFromFileUnderPath("files/output/");
        decodeString(rootElement, new StringBuilder(fromFile));
        System.out.println("\nDecoded String save to file");
        FileHelper.writeToFile("decoded", decodedStr);
    }


    public static void decodeString(Node rootEl, StringBuilder bits) {
        if (isLeaf(rootEl)) {
            var value = rootEl.getFreq();
            while (value-- > 0) {
                rootEl.setFreq(value);
                decodedStr.append(rootEl.getCharacter());
            }
        } else {
            int index = -1;
            while (index < bits.length() - 1) {
                index = decodeBits(rootEl, index, bits);
            }
        }
    }

    private static Map<Character, Integer> getCharacterIntegerMap(String text) {
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : text.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
        return freq;
    }


    private static void createNodeAndAddToPrioQueue(Map<Character, Integer> freq, NodePriorityQueue pq) {
        for (var entry : freq.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }
    }

    public static void encode(Node rootEl, String str,
                              Map<Character, String> huffmanCodeMap) {
        if (rootEl == null) {
            return;
        }
        if (isLeaf(rootEl)) {
            huffmanCodeMap.put(rootEl.getCharacter(), str.length() > 0 ? str : "1");
        }
        encode(rootEl.getLeft(), str + '0', huffmanCodeMap);
        encode(rootEl.getRight(), str + '1', huffmanCodeMap);
    }

    public static int decodeBits(Node rootEl, int index, StringBuilder sb) {
        if (Objects.isNull(rootEl)) {
            return index;
        }
        if (isLeaf(rootEl)) {
            decodedStr.append(rootEl.getCharacter());
            return index;
        }
        index++;
        rootEl = (sb.charAt(index) == '0') ? rootEl.getLeft() : rootEl.getRight();
        index = decodeBits(rootEl, index, sb);
        return index;
    }

}
