package jp.comprehesion.report.jpcomprehensionreport.event;

import jp.comprehesion.report.jpcomprehensionreport.service.WebParseService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClipboardEventListener implements ApplicationListener<ClipboardEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClipboardEventListener.class);
    private static final UrlValidator URL_VALIDATOR = new UrlValidator();

    private final WebParseService webParseService;

    @SneakyThrows
    @Override
    public void onApplicationEvent(ClipboardEvent event) {
        LOGGER.info("Received clipboard change: " + event.getClipboardContent());
        if (URL_VALIDATOR.isValid(event.getClipboardContent())) {
            webParseService.parseAndSave(event.getClipboardContent());
        }
    }
}
