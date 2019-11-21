import com.google.gson.Gson;
import kong.unirest.ObjectMapper;
import kong.unirest.Unirest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import static input.InputUtils.yesNoInput;



public class ec_API {

    public static Boolean moreInfo;
    public static Boolean saveYesorNo;

    public static void main(String[] args) {
        Unirest.config().setObjectMapper(new ObjectMapper() {
            private Gson gson = new Gson();

            @Override
            public <T> T readValue(String s, Class<T> aClass) {
                return gson.fromJson(s, aClass);
            }

            @Override
            public String writeValue(Object o) {
                return gson.toJson(o);
            }
        });


        do {
            String url = "http://www.boredapi.com/api/activity/";


            Bored statement = Unirest.get(url).asObject(Bored.class).getBody();

            String activitySuggestion = statement.activity;
            String typeSuggestion = statement.type;
            Integer numParticipants = statement.participantsNum;
            double suggestionPrice = statement.price;
            String LinkSuggestion = statement.link;


            System.out.println("Random Activity Suggestion: \n" +
                    activitySuggestion + "\nType: " + typeSuggestion + "\nParticipants: " + numParticipants +
                    "\nPrice: " + suggestionPrice + "\nLink for more: " + LinkSuggestion);


            saveYesorNo = yesNoInput("Do you want to save this activity to a file?");

            if (saveYesorNo) {
                writer(activitySuggestion, typeSuggestion, numParticipants, suggestionPrice, LinkSuggestion);
                moreInfo = yesNoInput("Do you want to load more suggestion?");
            } else {
                moreInfo = yesNoInput("Do you want to load more suggestion?");
            }


        }
        while (moreInfo);

        System.out.println("End of program");


    }















    private static void writer(String activity, String type, Integer numPart, double Price, String Link) {

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("BoredList.txt"))){

            writer.append(activity + "\n" + type + "\n" + numPart + "\n" + Price + "\n" + Link);

        }catch(IOException e){
            System.out.println("Error Message " + e);
        }


    }


}

class Bored {
    public String activity;
    public String type;
    public int participantsNum;
    public double price;
    public String link;

}


