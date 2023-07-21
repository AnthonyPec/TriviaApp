package com.example.triviaapp2.Model;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OpenTriviaAPI {

    @GET("posts")
    Call<List<Post>> getPost();

    @GET("api.php")
    Call<TriviaQuestion> getQuestions(@Query("amount") int amount, @Query("category") int postId,@Query("type") String type);
}

