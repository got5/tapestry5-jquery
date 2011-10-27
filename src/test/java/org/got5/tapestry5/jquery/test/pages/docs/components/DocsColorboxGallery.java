package org.got5.tapestry5.jquery.test.pages.docs.components;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.Import;
import org.got5.tapestry5.jquery.utils.JQueryTabData;

/**
 * Colorbox gallery component documentation.
 *
 * @author criedel
 */
@Import(stylesheet = "context:css/colorbox.css")
public class DocsColorboxGallery {

    public List<JQueryTabData> getListTabData() {

        List<JQueryTabData> listTabData = new ArrayList<JQueryTabData>();

        listTabData.add(new JQueryTabData("Documentation", "docs"));
        listTabData.add(new JQueryTabData("Example", "example"));

        return listTabData;

    }

}
