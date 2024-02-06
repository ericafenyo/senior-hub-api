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

package com.ericafenyo.seniorhub.controller;

import com.ericafenyo.seniorhub.controller.request.CreateUserRequest;
import com.ericafenyo.seniorhub.data.dao.UserDao;
import com.ericafenyo.seniorhub.data.entity.UserEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

  private UserDao dao;

  public UserController(UserDao dao) {
    this.dao = dao;
  }

  @PostMapping("users")
  public UserEntity createUser(CreateUserRequest user) {
    return dao.insert(user);
  }

  @GetMapping("users")
  public List<UserEntity> getUsers() {

    List<UserEntity> entities = dao.findAll();

    return entities;

//    var user = new User();
//    user.setId(UUID.randomUUID());
//    user.setName("John Doe");
//    user.setEmail("john.doe@example.com");
//    user.setPhotoUrl(new URL("https://example.com/photos/1"));
//    user.setCreatedAt(LocalDateTime.now().minusDays(10));
//    user.setUpdatedAt(LocalDateTime.now());
//
//    Address address = new Address();
//    address.setCountry("France");
//    address.setStreet("19 rue de la Procé");
//    address.setPostalCode("75100");
//    address.setCity("Paris");
//    address.setCountry("France");
//
//    user.setAddress(address);
//
//    return user;
  }
}
