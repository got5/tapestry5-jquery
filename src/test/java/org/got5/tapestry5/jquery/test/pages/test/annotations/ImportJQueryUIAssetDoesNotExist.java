package org.got5.tapestry5.jquery.test.pages.test.annotations;

import org.got5.tapestry5.jquery.ImportJQueryUI;

@ImportJQueryUI(value="wrongPath")
public class ImportJQueryUIAssetDoesNotExist extends AbstractImportJQueryUI {

}
