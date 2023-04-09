/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.domain.jooq;


import javax.annotation.processing.Generated;

import ru.tinkoff.edu.domain.jooq.tables.Chat;
import ru.tinkoff.edu.domain.jooq.tables.ChatLink;
import ru.tinkoff.edu.domain.jooq.tables.Link;


/**
 * Convenience access to all tables in the default schema.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.17.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>CHAT</code>.
     */
    public static final Chat CHAT = Chat.CHAT;

    /**
     * The table <code>CHAT_LINK</code>.
     */
    public static final ChatLink CHAT_LINK = ChatLink.CHAT_LINK;

    /**
     * The table <code>LINK</code>.
     */
    public static final Link LINK = Link.LINK;
}
