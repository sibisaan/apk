package com.videocalsecret;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        // Handle incoming messages and commands
        if (update.hasMessage() && update.getMessage().hasText()) {
            String command = update.getMessage().getText();
            switch (command) {
                case "/GETDEVICES":
                    // Call backend API to get devices
                    break;
                case "/SELECTDEVICE":
                    // Logic to select device
                    break;
                case "/UNSELECTDEVICE":
                    // Logic to unselect device
                    break;
                case "/GETACCOUNT":
                    // Logic to get account info
                    break;
                case "/GETNOTIFICATION":
                    // Logic to get notifications
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "YourBotUsername"; // Ganti dengan username bot Anda
    }

    @Override
    public String getBotToken() {
        return "YourBotToken"; // Ganti dengan token bot Anda
    }
}