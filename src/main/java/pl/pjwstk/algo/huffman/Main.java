package pl.pjwstk.algo.huffman;

import pl.pjwstk.algo.huffman.helper.FileHelper;

import java.io.IOException;

import static pl.pjwstk.algo.huffman.algo.HuffmanAlgo.*;

public class Main
{
    public static void main(String[] args) throws IOException {
        String text = FileHelper.textFromFileUnderPath("files/input/");
        createHuffmanTree(text);
        encodedStringSaveToFile();
        decodedContentSaveToFile();
    }
}



