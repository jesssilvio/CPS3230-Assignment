package org.example;

import com.google.gson.Gson;
import org.example.Utils.API;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Request {
    protected API API;

    public Request() {
    }

    public void setAPI(API API) {
        this.API = API;
    }

    public int sendPostRequestToApi(String jsonString) throws IOException, InterruptedException {
        if (API != null) {
            if (API.getApi() == API.INITIALISED) {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://api.marketalertum.com/Alert"))
                        .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                        .header("Content-Type", "application/json")
                        .build();

                HttpResponse<String> response = client.send(request,
                        HttpResponse.BodyHandlers.ofString());
                // 201
                // Created
                return response.statusCode();
            } else {
                return -1;
            }
        }
        return -1;
    }

    public int sendDeleteRequestToApi() throws IOException, InterruptedException {
        if (API != null) {
            if (API.getApi() == API.INITIALISED) {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://api.marketalertum.com/Alert?userId=4414c849-bd0e-4f98-9bd4-405c4d6df075\n"))
                        .DELETE()
                        .build();

                HttpResponse<String> response = client.send(request,
                        HttpResponse.BodyHandlers.ofString());

                // 200 OK
                return response.statusCode();
            } else {
                return -1;
            }
        }
        return -1;
    }

    public boolean sendFiveAlertsToWebsite(List<Alert> alertList) throws IOException, InterruptedException {
        for (int i = 0; i < alertList.size(); i++) {
            // Converting Alert Class to JsonObject
            String jsonString = new Gson().toJson(alertList.get(i));

            if (sendPostRequestToApi(jsonString) != 201) {
                return false;
            }
        }
        return true;
    }
}
