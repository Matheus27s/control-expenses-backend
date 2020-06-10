package control.expenses.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import control.expenses.model.Recipe;
import control.expenses.model.User;
import control.expenses.repository.UserRepository;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<User> get(@PathVariable(value = "id") Long id) {
		Optional<User> user =  userRepository.findById(id);
		return ResponseEntity.ok(user.get());
	}
	
	@PostMapping
	public ResponseEntity<User> insert( @RequestBody User user ) {
		
		if( user.getId() == null ) {
			
			List<Recipe> recipes = new ArrayList<Recipe>();
			Recipe recipe = new Recipe();
				recipe.setDateMonth(new Date());
				recipe.setValue(0f);
				recipe.setUser(user);
				recipes.add(recipe);
			user.setRecipes(recipes);
			user.setProfile("");
			
			user = userRepository.save(user);
		} 
		
		return ResponseEntity.ok(user);
	}
	
	@PutMapping
	public ResponseEntity<User> update( @RequestBody User user ) {
		
		if( user.getId() != null ) {
			
			String encodedString = Base64.getEncoder().encodeToString(user.getProfile().getBytes());
			user.setProfile(encodedString);
			
			user = userRepository.save(user);
		}
				
		user = userRepository.save(user);
		return ResponseEntity.ok(user);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> remove( @PathVariable( name = "id" ) Long id ) {
		userRepository.deleteById(id);
		return new ResponseEntity("OK!!", HttpStatus.OK);
	}

}
