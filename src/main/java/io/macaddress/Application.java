package io.macaddress;

import io.macaddress.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

@Slf4j
public class Application {
    private static String result = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;
        try {
            while (isContinue) {
                log.info("Enter MAC address (Press Q then enter to quite): ");
                String input = scanner.nextLine();
                if ("q".equalsIgnoreCase(input)) {
                    isContinue = false;
                } else {
                    result = HttpUtil.sendRequest(input);
                }
                log.info("Company Name for Mac Address '{}' is : {} ", input, result);
            }
        } catch (IllegalStateException | NoSuchElementException | IOException e) {
            log.error("Exiting..");
        }
    }
}
