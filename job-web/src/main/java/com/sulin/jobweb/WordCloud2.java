package com.sulin.jobweb;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.WordDictionary;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author sulin
 * @time 2020-02-24
 */
public class WordCloud2 {
    public static void main(String[] args) {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<String> words = WordCloudMain.loadWord();
        words.forEach(x->{
            System.out.println(segmenter.process(x, JiebaSegmenter.SegMode.SEARCH).toString());
        });
    }
}
