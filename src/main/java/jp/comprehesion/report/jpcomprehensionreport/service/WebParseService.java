package jp.comprehesion.report.jpcomprehensionreport.service;

import com.atilika.kuromoji.TokenBase;
import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WebParseService {

    private final CacheService cacheService;

    public void parseAndSave(String url) throws IOException {
        List<String> pageWords = getPageJpWords(url);

        List<String> jpdbWords = cacheService.getValues("jpdbWords");
        List<String> knownWords = new ArrayList<>(pageWords);
        knownWords.retainAll(jpdbWords);

        List<String> unknownWords = new ArrayList<>(CollectionUtils.subtract(pageWords, knownWords));

        cacheService.putValues("knownWords", knownWords);
        cacheService.putValues("pageWords", pageWords);
        cacheService.putValues("unknownWords", unknownWords);
    }

    private static List<String> getPageJpWords(String url) throws IOException {
        String urlText =
                Arrays.stream(Jsoup.connect(url).get().text().split("")).filter(
                    letter -> (letter.charAt(0) > '\u3040' && letter.charAt(0) < '\u4DBF') || (letter.charAt(0) > '\u4e00' && letter.charAt(0) < '\u9faf')
                ).collect(Collectors.joining());

        Tokenizer tokenizer = new Tokenizer() ;
        List<Token> tokens = tokenizer.tokenize(urlText);

        return tokens.stream().map(TokenBase::getSurface).toList();
    }
}
