package jp.comprehesion.report.jpcomprehensionreport.service;

import com.google.gson.Gson;
import jp.comprehesion.report.jpcomprehensionreport.model.jpdb.Jpdb;
import jp.comprehesion.report.jpcomprehensionreport.model.jpdb.Vocab;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SettingsService {

    private static final Gson GSON = new Gson();
    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsService.class);

    private final CacheService cacheService;

    public void saveJpdbFile(MultipartFile file) throws IOException {
        Jpdb jpdb = GSON.fromJson(new String(file.getBytes()), Jpdb.class);
        cacheService.reset("jpdbWords");
        cacheService.putValues("jpdbWords", jpdb.getCardsVocabularyJpEn().stream().map(Vocab::spelling).toList());
        LOGGER.info("Saved jpdb file");
    }
}
