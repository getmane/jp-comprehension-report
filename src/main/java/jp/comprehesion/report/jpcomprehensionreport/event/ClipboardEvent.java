package jp.comprehesion.report.jpcomprehensionreport.event;

import org.springframework.context.ApplicationEvent;

public class ClipboardEvent extends ApplicationEvent {

    private final String clipboardContent;

    public ClipboardEvent(Object source, String clipboardContent) {
        super(source);
        this.clipboardContent = clipboardContent;
    }

    public String getClipboardContent() {
        return clipboardContent;
    }
}
