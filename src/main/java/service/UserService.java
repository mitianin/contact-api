package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Data
public class UserService {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String tokenFile;

    private String baseUri = "https://mag-contacts-api.herokuapp.com";


    public List<User> getAll(){
        UserResponse userResponse = null;
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder().
                    uri(new URI(baseUri + "/users"))
                    .header("Accept", "application/json")
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            userResponse = objectMapper.readValue(response.body(), UserResponse.class);

        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (userResponse != null) return userResponse.getUsers();
        else return null;
    }

    public LoginResponse login(String login, String password) {

        try {
            String logData = objectMapper.writeValueAsString(new LoginRequest(login, password));

            HttpRequest httpRequest =
                    HttpRequest.newBuilder().
                    uri(new URI(baseUri + "/login"))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(logData))
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            LoginResponse loginResponse = objectMapper.readValue(response.body(), LoginResponse.class);

            if (loginResponse.getToken() != null) writeToFile(loginResponse.getToken());

            return loginResponse;


        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<FindContact> findByName(String name, String token){

        try {
            String findData =
                    objectMapper.writeValueAsString(new FindContact(name, "", "", ""));

            HttpRequest httpRequest = createPostRequestWithToken("/contacts/find", findData, token);

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            FindResponse findResponse = objectMapper.readValue(response.body(), FindResponse.class);


            return findResponse.getContacts()
                    .stream()
                    .filter((x) -> x.getName().equals(name))
                    .collect(Collectors.toList());

        } catch (URISyntaxException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<FindContact> findByValue(String value, String token){

        try {
            String findData =
                    objectMapper.writeValueAsString(new FindContact("", value, "", ""));

            HttpRequest httpRequest = createPostRequestWithToken("/contacts/find", findData, token);

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            FindResponse findResponse = objectMapper.readValue(response.body(), FindResponse.class);

            return  findResponse.getContacts().
                    stream()
                    .filter((x) -> x.getValue().equals(value))
                    .collect(Collectors.toList());

        } catch (URISyntaxException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<FindContact> findAllContacts(String token){

        try {

            HttpRequest httpRequest = HttpRequest.newBuilder().
                    uri(new URI(baseUri + "/contacts"))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            FindResponse findResponse = objectMapper.readValue(response.body(), FindResponse.class);

            return  findResponse.getContacts();

        } catch (URISyntaxException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AddResponse add(String type, String value, String name, String token){
        AddResponse addResponse = null;
        try {
            String addData = objectMapper.writeValueAsString(new AddRequest(type, value, name));
            HttpRequest httpRequest = createPostRequestWithToken("/contacts/add", addData, token);

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            addResponse = objectMapper.readValue(response.body(), AddResponse.class);

        } catch (URISyntaxException | InterruptedException | IOException e) {
            e.printStackTrace();
        }

        return addResponse;
    }

    public List<User> getAllWithLogin(String token){
        UserResponse userResponse = null;
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder().
                    uri(new URI(baseUri + "/users2"))
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            userResponse = objectMapper.readValue(response.body(), UserResponse.class);

        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (userResponse != null) return userResponse.getUsers();
        else return null;
    }

    public RegisterResponse register(String login, String password, String dateBorn){
        RegisterResponse registerResponse = null;
        try {
            String regData = objectMapper.writeValueAsString(new RegisterRequest(login, password, dateBorn));
            HttpRequest httpRequest =
                    HttpRequest.newBuilder().
                    uri(new URI(baseUri + "/register"))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(regData))
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            registerResponse = objectMapper.readValue(response.body(), RegisterResponse.class);

        } catch (URISyntaxException | InterruptedException | IOException e) {
            e.printStackTrace();
        }

        return registerResponse;
    }

    public String getToken(){
        String token = null;

        if (!checkTokenTime()) return null;

        try (BufferedReader br = new BufferedReader(new FileReader(tokenFile))){
            token = String.valueOf(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }


    private void writeToFile(String token){

        try (FileWriter fw = new FileWriter(tokenFile)){
            fw.write(token);
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HttpRequest createPostRequestWithToken(String path, String data, String token) throws URISyntaxException {
        return HttpRequest.newBuilder().
                uri(new URI(baseUri + path))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .POST(HttpRequest.BodyPublishers.ofString(data))
                .build();

    }

    private boolean checkTokenTime(){
        return new Date().getTime() - new File(tokenFile).lastModified() <= 600000;
    }




}
