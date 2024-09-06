package jp.comprehesion.report.jpcomprehensionreport.controller;

import jp.comprehesion.report.jpcomprehensionreport.constant.Constants;
import jp.comprehesion.report.jpcomprehensionreport.service.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
class HomeController {

    private final CacheService cacheService;

    @GetMapping("/")
    public String main(Model model) {
        List<String> unknownWords = cacheService.getValues(Constants.CACHE_UNKNOWN_WORDS_REPOSITORY);
        List<String> knownWords = cacheService.getValues(Constants.CACHE_KNOWN_WORDS_REPOSITORY);
        List<String> pageWords = cacheService.getValues(Constants.CACHE_PAGE_WORDS_REPOSITORY);

        model.addAttribute(Constants.CACHE_UNKNOWN_WORDS_REPOSITORY, unknownWords);
        model.addAttribute(Constants.CACHE_KNOWN_WORDS_REPOSITORY, knownWords);
        model.addAttribute(Constants.CACHE_PAGE_WORDS_REPOSITORY, pageWords);
        model.addAttribute("knownPercentage",
                !pageWords.isEmpty()
                        ? String.format("%.0f", (double) knownWords.size() / (double) pageWords.size() * 100)
                        : "0");
        return "home";
    }
}