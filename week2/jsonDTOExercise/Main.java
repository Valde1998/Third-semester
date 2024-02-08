package jsonDTOExercise;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;


public class Main {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
        File file = new File("C:\\Users\\valde\\OneDrive\\Dokumenter\\GitHub\\Week1\\week2\\jsonDTOExercise\\account.json");

        Account[] accounts = jsonToAccount(file);
        System.out.println(Arrays.toString(accounts));
        AccountDTO accountDTO = accountToDTO(accounts[0]);
        System.out.println(accountDTO);
        AccountDTO[] accountDTOS = accountsToDTOS(accounts);
        printDTOS(accountDTOS);

    }


    public static Account[] jsonToAccount(File jsonFile) {
        try (Scanner scanner = new Scanner(Paths.get(jsonFile.getAbsolutePath()), StandardCharsets.UTF_8.name());){
            String json = scanner.useDelimiter("\\A").next();
            JsonElement element = JsonParser.parseString(json);
            System.out.println(element);
            if(element.isJsonObject()){
                JsonObject jsonObject = element.getAsJsonObject();
                ResultDTO result = gson.fromJson(jsonObject,ResultDTO.class);
                return result.accounts;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static AccountDTO accountToDTO(Account account){
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setFullName(account.getFirstName()+" "+account.getLastName());
        accountDTO.setCity(account.getAddress().getCity());
        accountDTO.setZipcode(accountDTO.getZipcode());
        accountDTO.setActive(accountDTO.isActive());
        return accountDTO;
    }

    public static AccountDTO[] accountsToDTOS(Account[] accounts){
        AccountDTO[] result = new AccountDTO[accounts.length];
        for(int i = 0; i < result.length; i++){
            result[i] = accountToDTO(accounts[i]);
        }
        return result;
    }

    public static void printDTOS(AccountDTO[] accountDTOS){
        for(int i = 0; i < accountDTOS.length; i++){
            System.out.println("Account number: "+i);
            System.out.println("Full name: "+accountDTOS[i].getFullName());
            System.out.println("City: "+accountDTOS[i].getCity());
            System.out.println("Zipcode "+accountDTOS[i].getZipcode());
            System.out.println("Active "+accountDTOS[i].isActive());
            System.out.println(" ");
        }
    }
}
