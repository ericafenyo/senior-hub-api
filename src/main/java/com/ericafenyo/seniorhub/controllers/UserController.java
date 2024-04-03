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

import com.ericafenyo.seniorhub.dto.UserCreationDto;
import com.ericafenyo.seniorhub.dto.UserUpdateDto;
import com.ericafenyo.seniorhub.model.User;
import com.ericafenyo.seniorhub.services.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

  private final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @GetMapping("users")
  public List<User> getUsers() {
    return service.getUsers();
  }

  @GetMapping("users/{id}")
  public User getUserById(@PathVariable String id) throws Exception {
    return service.getUserById(id);
  }

  @PostMapping("users")
  public User createUser(@RequestBody @Valid UserCreationDto userCreationDto) throws Exception {
    return service.createUser(userCreationDto);
  }

  @PutMapping("users/{id}")
  public User updateUser(@PathVariable String id, @RequestBody @Valid UserUpdateDto userUpdateDto) {
    return service.updateUser(id, userUpdateDto);
  }

  @DeleteMapping("users/{id}")
  public void deleteUser(@PathVariable String id) {
    service.deleteUser(id);
  }
}