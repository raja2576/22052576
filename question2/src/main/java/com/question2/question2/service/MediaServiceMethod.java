package com.question2.question2.service;

import java.util.List;

import com.question2.question2.Modal.Post;
import com.question2.question2.Modal.User;

public interface MediaServiceMethod {
    List<User> getTopUsers();
    List<Post> getPostsByType(String type);
}

	
	
