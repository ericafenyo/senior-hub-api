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

package com.ericafenyo.seniorhub.services;

import com.ericafenyo.seniorhub.dto.CreateTeamRequest;
import com.ericafenyo.seniorhub.dto.CreateUserRequest;
import com.ericafenyo.seniorhub.dto.UserUpdateDto;
import com.ericafenyo.seniorhub.exceptions.HttpException;
import com.ericafenyo.seniorhub.model.Team;
import com.ericafenyo.seniorhub.model.User;

import java.util.List;


public interface UserService {
    List<User> getUsers();

    User getUserById(String id) throws HttpException;

    User createUser(CreateUserRequest userCreationDto) throws HttpException;

    User updateUser(String id, UserUpdateDto updateUserDto);

    void deleteUser(String id);

    List<Team> getUserTeams(String id) throws HttpException;

    Team createTeam(String id, CreateTeamRequest request) throws HttpException;
}
