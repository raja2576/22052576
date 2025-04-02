package com.question2.question2.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.question2.question2.ExternalClient;
import com.question2.question2.Modal.Post;
import com.question2.question2.Modal.User;

@Service
public class SocialmediaService implements MediaServiceMethod {
	private final ExternalClient  externalClient  ;

    public SocialmediaService(ExternalClient externalApiClient) {
        this.externalClient = externalApiClient;
    }

    @Override
    public List<User> getTopUsers() {
        List<User> users = externalClient.getUsers();
        Map<Integer, Integer> postCounts = externalClient.getUserPostCounts();

        return users.stream()
                .peek(user -> user.postCount = postCounts.getOrDefault(user.id, 0))
                .sorted(Comparator.comparingInt((User u) -> u.postCount).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    @Override
    public List<Post> getPostsByType(String type) {
        List<Post> posts = externalClient.getPosts();

        if ("latest".equalsIgnoreCase(type)) {
            return posts.stream()
                    .sorted(Comparator.comparing(Post::getDate).reversed()) // Assuming Post has a getDate() method
                    .limit(5)
                    .collect(Collectors.toList());
        } else if ("popular".equalsIgnoreCase(type)) {
            return posts.stream()
                    .sorted(Comparator.comparingInt(Post::getCommentCount).reversed()) // Assuming Post has getCommentCount()
                    .limit(5)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }


}