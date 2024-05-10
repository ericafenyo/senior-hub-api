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

package com.ericafenyo.seniorhub.mapper;

import com.ericafenyo.seniorhub.entities.AddressEntity;
import com.ericafenyo.seniorhub.entities.RoleEntity;
import com.ericafenyo.seniorhub.model.Address;
import com.ericafenyo.seniorhub.model.Role;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * Maps a {@link AddressEntity} to a {@link Address}.
 */
@Component
public class RoleMapper implements Function<RoleEntity, Role> {

    /**
     * Converts a {@link RoleEntity} to a {@link Role}.
     *
     * @param entity The input role entity.
     * @return The mapped role object.
     */
    @Override
    public Role apply(RoleEntity entity) {
        var role = new Role();
        role.setId(entity.getUuid());
        role.setName(entity.getName());
        role.setSlug(entity.getSlug());
        role.setDescription(entity.getDescription());
        return role;
    }
}
