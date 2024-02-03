package jp.comprehesion.report.jpcomprehensionreport.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

@Component
public class ClipboardEventPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClipboardEventPublisher.class);

    private final ApplicationEventPublisher applicationEventPublisher;

    public ClipboardEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
        Toolkit.getDefaultToolkit().getSystemClipboard().addFlavorListener(
                e -> publishCustomEvent(getClipboardContent())
        );
    }

    private String getClipboardContent() {
        try {
            return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getContents(this).getTransferData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException | IOException ex) {
            LOGGER.error("Clipboard event publish failure. ", ex);
        }
        return null;
    }

    public void publishCustomEvent(final String clipboardContent) {
        LOGGER.info("Clipboard changed. Publishing");
        ClipboardEvent event = new ClipboardEvent(this, clipboardContent);
        applicationEventPublisher.publishEvent(event);
    }
}
