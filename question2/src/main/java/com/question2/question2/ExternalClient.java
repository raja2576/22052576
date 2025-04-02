package com.question2.question2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.question2.question2.Modal.Post;
import com.question2.question2.Modal.User;

@Component
	public class ExternalClient {
		public List<User> getUsers() {
	        return List.of(new User()); 
	    }
	    
	    public Map<Integer, Integer> getUserPostCounts() {
	        return new HashMap<>(); 
	    }
	    
	    public List<Post> getPosts() {
	        return List.of(new Post());
	    }

	}

