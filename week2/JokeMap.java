import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.ToString;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class JokeMap {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static void main(String[] args) {
        JokeMap jm = new JokeMap();
        JokeDTO jokeDTO = jm.getJoke();
        System.out.println(jokeDTO.joke);

    }

    private String responseBody(String url) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .method("GET", null)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            String res = response.body().string();
            System.out.println(res);
            return res;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public JokeDTO getJoke(){
        String url = "https://icanhazdadjoke.com/";
        String res = responseBody(url);
        JokeDTO jokeDTO = gson.fromJson(res, JokeDTO.class);
        return jokeDTO;
    }

    @Getter
    @ToString
    public class JokeDTO {
        private String id;
        private String joke;
        private int status;
    }
}