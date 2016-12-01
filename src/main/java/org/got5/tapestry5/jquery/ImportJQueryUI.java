package org.got5.tapestry5.jquery;

import static org.apache.tapestry5.ioc.annotations.AnnotationUseContext.*;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.tapestry5.ioc.annotations.UseWith;

/**
 * Annotation used to load JavaScript files from the jQuery-UI project
 * @deprecated
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@UseWith({ COMPONENT, MIXIN, PAGE })
public @interface ImportJQueryUI {

    /**
     * @return the base name of a jQuery script that should be imported.
     * @see JQuerySymbolConstants#JQUERY_UI_PATH
     */
    String[] value() default {};

    String theme() default "";
}
