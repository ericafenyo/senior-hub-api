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

package com.ericafenyo.seniorhub.implementation.repositories;

import com.ericafenyo.seniorhub.dao.CredentialDao;
import com.ericafenyo.seniorhub.entities.CredentialEntity;
import com.ericafenyo.seniorhub.repository.CredentialRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DefailtCredentialRepository implements CredentialRepository {

  private final CredentialDao dao;

  public DefailtCredentialRepository(CredentialDao dao) {
    this.dao = dao;
  }

  @Override
  public Optional<CredentialEntity> findByUserId(Long userId) {
    return dao.findByUserId(userId);
  }

  @Override
  public CredentialEntity save(CredentialEntity entity) {
    return dao.save(entity);
  }
}