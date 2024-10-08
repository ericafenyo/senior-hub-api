/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2024 Eric Afenyo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.ericafenyo.seniorhub.controllers;

import com.ericafenyo.seniorhub.dto.CreateTeamRequest;
import com.ericafenyo.seniorhub.dto.CreateUserRequest;
import com.ericafenyo.seniorhub.dto.UserUpdateDto;
import com.ericafenyo.seniorhub.exceptions.HttpException;
import com.ericafenyo.seniorhub.model.Team;
import com.ericafenyo.seniorhub.model.User;
import com.ericafenyo.seniorhub.services.UserService;
import com.ericafenyo.seniorhub.util.Accounts;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody CreateUserRequest request) throws Exception {
        return service.createUser(request);
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return service.getUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable String id) throws Exception {
        return service.getUserById(id);
    }

    @GetMapping("/users/me")
    public Object getAuthenticatedUser(Authentication authentication) throws Exception {
        String userId = Accounts.extractUserId(authentication);
        return service.getUserById(userId);
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable String id, @RequestBody @Valid UserUpdateDto userUpdateDto) {
        return service.updateUser(id, userUpdateDto);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable String id) {
        service.deleteUser(id);
    }

    // Team sub-resources
    @PostMapping("/users/{id}/teams")
    public Team createTeam(@PathVariable String id, @RequestBody CreateTeamRequest request) throws HttpException {
        return service.createTeam(id, request);
    }

    @GetMapping("/users/{id}/teams")
    public List<Team> getUserTeams(@PathVariable String id) throws HttpException {
        return service.getUserTeams(id);
    }
}
