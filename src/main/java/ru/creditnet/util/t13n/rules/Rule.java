package ru.creditnet.util.t13n.rules;

import org.jetbrains.annotations.NotNull;
import ru.creditnet.util.t13n.Split;

/**
 * @author astepachev
 */
public interface Rule {
    @NotNull
    Split split(@NotNull CharSequence seq);
}
