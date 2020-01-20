package com.qaprosoft.zafira.manager;

import com.qaprosoft.zafira.domain.EmailMsg;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class EmailManager {

    private static final Logger LOGGER = Logger.getLogger(EmailManager.class);

    private static final int DEFAULT_NOTIFICATION_TIMEOUT = 500;
    private static final int DEFAULT_EMAILS_COUNT_TO_RETRIEVE = 10;
    private static final int FLUENT_WAIT_POLLING_INTERVAL = 5;

    private String email;
    private String password;

    private static Session session;

    public EmailManager(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    private static Properties initProps() {
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imap.socketFactory.fallback", "false");

        return props;
    }

    public EmailMsg[] getInbox(int lastEmailsCount) {
        // open email and get inbox
        if (session == null) {
            Properties props = initProps();
            session = Session.getInstance(props, null);
            session.setDebug(true);
        }
        Store store = null;
        try {
            // open session
            store = session.getStore("imaps");
            store.connect("imap.gmail.com", email, password);

            // get inbox folder
            Folder inboxFolder = store.getFolder("inbox");
            inboxFolder.open(Folder.READ_ONLY);
            if (inboxFolder.isOpen()) {
                Message[] messages = inboxFolder.getMessages();
                EmailMsg[] finalMessages = new EmailMsg[0];
                int startIndex = messages.length - 1;
                int endIndex = (messages.length > lastEmailsCount) ? (messages.length - lastEmailsCount) : 0;
                for (int i = startIndex; i >= endIndex; i--) {
                    String content = "";
                    String contentSrc = "";
                    try {
                        content = ((MimeMultipart) messages[i].getContent()).getBodyPart(0).getContent().toString();
                        contentSrc = ((MimeMultipart) messages[i].getContent()).getBodyPart(1).getContent().toString();
                    } catch (ArrayIndexOutOfBoundsException e) {
                        content = ((MimeMultipart) messages[i].getContent()).getBodyPart(0).getContent().toString();
                    } catch (ClassCastException e) {
                        content = messages[i].getContent().toString();
                    }
                    finalMessages = (EmailMsg[]) ArrayUtils.add(finalMessages, new EmailMsg(messages[i].getSubject(),
                            messages[i].getFrom().toString(), messages[i].getReceivedDate(), content, contentSrc));
                }
                inboxFolder.close(false);
                store.close();
                return finalMessages;
            } else {
                store.close();
                throw new RuntimeException("Can't retrieve Inbox emails. Inbox folder isn't opened");
            }

        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            throw new RuntimeException("Can't retrieve Inbox emails", e);
        } finally {
            if (store != null) {
                try {
                    store.close();
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    // help method to verify INBOX email contains new mail
    public void waitForEmailDelivered(final Date startTestTime, final String testRunUrl) {
        Wait<Boolean> waiter = new FluentWait<>(false).withTimeout(Duration.ofSeconds(DEFAULT_NOTIFICATION_TIMEOUT))
                .pollingEvery(Duration.ofSeconds(FLUENT_WAIT_POLLING_INTERVAL));
        try {
            waiter.until(t -> {
                LOGGER.info("----------------------New round of emails retrieving--------------------------");
                EmailMsg[] messages = getInbox(DEFAULT_EMAILS_COUNT_TO_RETRIEVE);
                for (EmailMsg msg : messages) {
                    LOGGER.info("Retrieved msg: " + msg.toString());
                    if ((msg.getTime().compareTo(startTestTime) > 0)
                            && (msg.getContent().contains(testRunUrl))) {
                        return true;
                    }
                }
                return false;
            });
        } catch (TimeoutException e) {
            throw new RuntimeException(String.format("Email with '%s' not delivered after %d sec waiting", testRunUrl,
                    DEFAULT_NOTIFICATION_TIMEOUT));
        }
        LOGGER.info("Email with '" + testRunUrl + "' with proper delivery date found in Inbox");
    }

    public EmailMsg readEmail(final Date startTestTime, final String testRunUrl) {
        waitForEmailDelivered(startTestTime, testRunUrl);
        EmailMsg[] messages = getInbox(DEFAULT_EMAILS_COUNT_TO_RETRIEVE);
        for (EmailMsg msg : messages) {
            if ((msg.getTime().compareTo(startTestTime) > 0)
                     && (msg.getContent().contains(testRunUrl))) {
                return msg;
            }
        }
        throw new RuntimeException("Expected email with '" + testRunUrl + "' not found in Inbox");
    }

}
