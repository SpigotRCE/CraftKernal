package io.spigotrce.craftkernal.common.discord.webhook;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DiscordWebhook {
    /**
     * webhookUrl string representing the discord webhook url
     */
    private final String webhookUrl;

    /**
     * Constructor to initialize webhookUrl and logger
     *
     * @param webhookUrl webhook url string
     */
    public DiscordWebhook(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    /**
     * Method to send a message to discord webhook using the provided json payload
     *
     * @param jsonPayload json payload string
     *
     * @throws IOException if an error occurs while sending the message
     */
    public void sendMessage(String jsonPayload) throws IOException {
        HttpURLConnection connection = getHttpURLConnection();


        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();

        if (responseCode < 200 || responseCode > 300)
            throw new IOException("Failed to send message: HTTP error code: " + responseCode);

        connection.disconnect();
    }

    /**
     * Method to send a raw message the discord webhook
     *
     * @param message the raw message to send
     *
     * @throws IOException if an error occurs while sending the message
     */
    public void sendRawMessage(String message) throws IOException {
        String jsonPayload = "{\"content\": \"" + escapeJson(message) + "\"}";
        sendMessage(jsonPayload);
    }

    /**
     * Method to escape JSON strings to ensure they are safe for Discord
     */
    private String escapeJson(String message) {
        return message.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }

    /**
     * Method to create a HttpURLConnection object with necessary headers
     *
     * @return HttpURLConnection object
     *
     * @throws IOException if an error occurs while creating the connection
     */
    private @NotNull HttpURLConnection getHttpURLConnection() throws IOException {
        URL url = new URL(webhookUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        return connection;
    }
}
