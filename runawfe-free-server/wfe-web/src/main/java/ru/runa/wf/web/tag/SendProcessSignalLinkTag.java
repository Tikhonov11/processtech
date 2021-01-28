package ru.runa.wf.web.tag;

import org.tldgen.annotations.BodyContent;
import ru.runa.common.web.Commons;
import ru.runa.common.web.tag.LinkTag;
import ru.runa.wf.web.MessagesProcesses;
import ru.runa.wfe.commons.web.PortletUrlType;

@org.tldgen.annotations.Tag(bodyContent = BodyContent.EMPTY, name = "sendProcessSignalLink")
public class SendProcessSignalLinkTag extends LinkTag {
    private static final long serialVersionUID = 1L;

    @Override
    protected String getLinkText() {
        return MessagesProcesses.LABEL_SEND_PROCESS_SIGNAL.message(pageContext);
    }

    private static final String FORWARD = "send_process_signal";

    @Override
    protected boolean isLinkEnabled() {
        return true;
    }

    @Override
    protected String getHref() {
        return Commons.getForwardUrl(FORWARD, pageContext, PortletUrlType.Render);
    }
}
