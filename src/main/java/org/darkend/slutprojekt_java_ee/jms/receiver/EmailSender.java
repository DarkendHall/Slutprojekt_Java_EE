package org.darkend.slutprojekt_java_ee.jms.receiver;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EmailSender {

    private final ClientOptions options = ClientOptions.builder()
            .apiKey(System.getenv("MJ_PUBLIC_KEY"))
            .apiSecretKey(System.getenv("MJ_SECRET_KEY"))
            .build();

    private final MailjetClient client = new MailjetClient(options);

    public MailjetResponse sendMail(String msg, List<String> recipients) throws MailjetException {
        var request = new MailjetRequest(Emailv31.resource);
        request.property(Emailv31.MESSAGES, new JSONArray()
                .put(new JSONObject().put(Emailv31.Message.FROM, new JSONObject().put("Email", "ags_ownz@hotmail.com")
                                .put("Name", "School System"))
                        .put(Emailv31.Message.TO, getRecipients(recipients))
                        .put(Emailv31.Message.SUBJECT, "Important message from your School!")
                        .put(Emailv31.Message.TEXTPART, msg)));
        return client.post(request);
    }

    private JSONObject[] getRecipients(List<String> recipients) {

        List<JSONObject> jsonObjectsList = new ArrayList<>();
        for (var recipient : recipients) {
            jsonObjectsList.add(new JSONObject().put("Email", recipient));
        }

        var jsonObjectsArray = new JSONObject[jsonObjectsList.size()];

        for (int i = 0; i < jsonObjectsArray.length; i++) {
            jsonObjectsArray[i] = jsonObjectsList.get(i);
        }

        return jsonObjectsArray;
    }
}
