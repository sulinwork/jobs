//package com.sulin.jobweb;
//
//import com.kennycason.kumo.CollisionMode;
//import com.kennycason.kumo.ParallelLayeredWordCloud;
//import com.kennycason.kumo.WordCloud;
//import com.kennycason.kumo.WordFrequency;
//import com.kennycason.kumo.bg.CircleBackground;
//import com.kennycason.kumo.bg.PixelBoundryBackground;
//import com.kennycason.kumo.bg.RectangleBackground;
//import com.kennycason.kumo.font.KumoFont;
//import com.kennycason.kumo.font.scale.LinearFontScalar;
//import com.kennycason.kumo.font.scale.SqrtFontScalar;
//import com.kennycason.kumo.image.AngleGenerator;
//import com.kennycason.kumo.nlp.FrequencyAnalyzer;
//import com.kennycason.kumo.nlp.normalize.*;
//import com.kennycason.kumo.nlp.tokenizers.ChineseWordTokenizer;
//import com.kennycason.kumo.palette.ColorPalette;
//import com.kennycason.kumo.palette.LinearGradientColorPalette;
//import com.kennycason.kumo.wordstart.CenterWordStart;
//
//import java.awt.*;
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author sulin
// * @time 2020-02-24
// */
//public class WordCloudMain {
//
////    private static final List<String> words = new ArrayList<>();
//
//    public static void main(String[] args) throws IOException {
//        List<String> words = loadWord();
////        //建立词频分析器，设置词频，以及词语最短长度，此处的参数配置视情况而定即可
////        FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
////        frequencyAnalyzer.setMinWordLength(4);
////        frequencyAnalyzer.setWordFrequenciesToReturn(300);
////
////        //引入中文解析器
////        frequencyAnalyzer.setWordTokenizer(new ChineseWordTokenizer());
////        //加入我们准备好的的词
////        List<WordFrequency> wordFrequencyList = frequencyAnalyzer.load(words);
////        //设置图片分辨率
////        Dimension dimension = new Dimension(1920, 1080);
////        //此处的设置采用内置常量即可，生成词云对象
////        WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
////        //设置字体和边界
////        wordCloud.setPadding(6);
////        Font font = new Font("STSong-Light", 2, 40);
////        wordCloud.setColorPalette(new LinearGradientColorPalette(Color.RED, Color.BLUE, Color.GREEN, 30, 30));
////        wordCloud.setKumoFont(new KumoFont(font));
////        //设置背景
////        //wordCloud.setBackground(new PixelBoundryBackground("C:\\Users\\sulin\\Pictures\\wordcloud_1.jpg"));
////        wordCloud.setBackgroundColor(new Color(255, 255, 255));
////        //设置背景图层为圆形
////        wordCloud.setBackground(new CircleBackground(255));
////        wordCloud.setFontScalar(new SqrtFontScalar(12, 45));
////        //生成词云
////        wordCloud.build(wordFrequencyList);
////        wordCloud.writeToFile("D:\\词云.png");
//        func1(words);
//
//    }
//
//    public static void func1(List<String> words) throws IOException {
//        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
//        frequencyAnalyzer.setWordFrequenciesToReturn(500);
//        frequencyAnalyzer.setMinWordLength(4);
//
//        frequencyAnalyzer.setWordTokenizer(new ChineseWordTokenizer());
//        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(words);
//
//        final Dimension dimension = new Dimension(500, 312);
//        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.RECTANGLE);
//        wordCloud.setPadding(4);
//        wordCloud.setBackground(new PixelBoundryBackground("C:\\Users\\sulin\\Pictures\\word_back.png"));
//        wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
//        wordCloud.setFontScalar(new LinearFontScalar(10, 40));
//        wordCloud.build(wordFrequencies);
//        wordCloud.writeToFile("D:\\词云.png");
//    }
//
//    public static void func2(List<String> words) {
//        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
//        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(words);
//        final Dimension dimension = new Dimension(600, 600);
//        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
//
//        wordCloud.setPadding(2);
//        wordCloud.setBackground(new CircleBackground(300));
//        wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
//        wordCloud.setFontScalar(new SqrtFontScalar(10, 40));
//        wordCloud.build(wordFrequencies);
//        wordCloud.writeToFile("D:\\词云.png");
//    }
//
//    public static void func3(List<String> words) {
//        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
//        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(words);
//        final Dimension dimension = new Dimension(600, 600);
//        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.RECTANGLE);
//        wordCloud.setPadding(0);
//        wordCloud.setBackground(new RectangleBackground(dimension));
//        wordCloud.setColorPalette(new ColorPalette(Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE));
//        wordCloud.setFontScalar(new LinearFontScalar(10, 40));
//        wordCloud.build(wordFrequencies);
//        wordCloud.writeToFile("D:\\词云.png");
//    }
//
//    /**
//     * 渐变 还可以
//     *
//     * @param words
//     */
//    public static void func4(List<String> words) {
//        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
//        frequencyAnalyzer.setWordFrequenciesToReturn(500);
//        frequencyAnalyzer.setMinWordLength(4);
//        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(words);
//        final Dimension dimension = new Dimension(600, 600);
//        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
//        wordCloud.setPadding(2);
//        wordCloud.setBackground(new CircleBackground(300));
//// colors followed by and steps between
//        wordCloud.setColorPalette(new LinearGradientColorPalette(Color.RED, Color.BLUE, Color.GREEN, 30, 30));
//        wordCloud.setFontScalar(new SqrtFontScalar(10, 40));
//        wordCloud.build(wordFrequencies);
//        wordCloud.writeToFile("D:\\词云.png");
//    }
//
//    public static void func5(List<String> words) {
//        final Dimension dimension = new Dimension(2000, 2000);
//        ParallelLayeredWordCloud parallelLayeredWordCloud = new ParallelLayeredWordCloud(4, dimension, CollisionMode.PIXEL_PERFECT);
//
//// Setup parts for word clouds
//        final Normalizer[] NORMALIZERS = new Normalizer[]{
//                new UpperCaseNormalizer(),
//                new LowerCaseNormalizer(),
//                new BubbleTextNormalizer(),
//                new StringToHexNormalizer()
//        };
//        final Font[] FONTS = new Font[]{
//                new Font("Lucida Sans", Font.PLAIN, 10),
//                new Font("Comic Sans", Font.PLAIN, 10),
//                new Font("Yu Gothic Light", Font.PLAIN, 10),
//                new Font("Meiryo", Font.PLAIN, 10)
//        };
//        final List<List<WordFrequency>> listOfWordFrequencies = new ArrayList<>();
//        final Point[] positions = new Point[]{
//                new Point(0, 0), new Point(0, 1000), new Point(1000, 0), new Point(1000, 1000)
//        };
//        final Color[] colors = new Color[]{Color.RED, Color.WHITE, new Color(0x008080)/* TEAL */, Color.GREEN};
//
//        // set up word clouds
//        for (int i = 0; i < 4; i++) {
//            final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
//            frequencyAnalyzer.setMinWordLength(3);
//            frequencyAnalyzer.setNormalizer(NORMALIZERS[i]);
//            frequencyAnalyzer.setWordFrequenciesToReturn(1000);
//            listOfWordFrequencies.add(frequencyAnalyzer.load(words));
//
//            final WordCloud worldCloud = parallelLayeredWordCloud.getAt(i);
//            worldCloud.setAngleGenerator(new AngleGenerator(0));
//            worldCloud.setPadding(3);
//            worldCloud.setWordStartStrategy(new CenterWordStart());
//            worldCloud.setKumoFont(new KumoFont(FONTS[i]));
//            worldCloud.setColorPalette(new ColorPalette(colors[i]));
//
//            worldCloud.setBackground(new RectangleBackground(positions[i], dimension));
//            worldCloud.setFontScalar(new LinearFontScalar(10, 40));
//        }
//
//        // start building
//        for (int i = 0; i < 4; i++) {
//            parallelLayeredWordCloud.build(i, listOfWordFrequencies.get(i));
//        }
//
//        parallelLayeredWordCloud.writeToFile("D:\\词云.png");
//    }
//
//    public static List<String> loadWord() {
//        List<String> words = new ArrayList<>();
//        FileReader fr = null;
//        BufferedReader br = null;
//        try {
//            fr = new FileReader(new File("D:\\20200220225022.txt"));
//            br = new BufferedReader(fr);
//            String line;
//            while ((line = br.readLine()) != null) {
//                words.add(line.split("#")[9]);
//            }
//            br.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return words;
//    }
//}
