package nl.tdegroot.games.bote.common.event;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EventListener {
    String value();
}
