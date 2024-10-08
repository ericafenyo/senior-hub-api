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

package com.ericafenyo.seniorhub;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class Messages {
    public static final String ERROR_RESOURCE_WITH_ID_NOTFOUND = "error.resource.with.id.not.found";
    public static final String ERROR_REQUESTED_RESOURCE_WITH_ID_NOT_FOUND = "error.requested.resource.with.id.not.found";
    public static final String ERROR_RESOURCE_NOTFOUND = "error.resource.not.found";
    public static final String ERROR_REQUESTED_RESOURCE_NOT_FOUND = "error.requested.resource.not.found";
    public static final String ERROR_RESOURCE_NOTFOUND_CODE = "error.resource.not.found.code";

    public static final String ERROR_RESOURCE_ALREADY_EXISTS = "error.resource.already.exists";
    public static final String ERROR_RESOURCE_ALREADY_EXISTS_CODE = "error.resource.already.exists.code";

    public static final String MESSAGE_INVITATION_ACCEPTED = "message.invitation.accepted";

    private final MessageSource source;

    public String get(String key) {
        return source.getMessage(key, null, Locale.getDefault());
    }

    public String format(String key, Object... args) {
        return source.getMessage(key, args, Locale.getDefault());
    }
}