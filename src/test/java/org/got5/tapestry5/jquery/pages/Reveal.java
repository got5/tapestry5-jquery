package org.got5.tapestry5.jquery.pages;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

public class Reveal {

    @InjectComponent
    private org.apache.tapestry5.corelib.components.Zone zzzone;

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    void onActionFromUpdateZone() {

        ajaxResponseRenderer.addRender(zzzone);
    }
}
