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

import com.ericafenyo.seniorhub.EnvironmentVariables;
import com.ericafenyo.seniorhub.Messages;
import com.ericafenyo.seniorhub.dto.AcceptInvitationRequest;
import com.ericafenyo.seniorhub.dto.ValidateInvitationRequest;
import com.ericafenyo.seniorhub.entities.InvitationEntity;
import com.ericafenyo.seniorhub.exceptions.HttpException;
import com.ericafenyo.seniorhub.exceptions.NotFoundException;
import com.ericafenyo.seniorhub.exceptions.invitation.InvitationAlreadyUsedException;
import com.ericafenyo.seniorhub.exceptions.invitation.InvitationExpiredException;
import com.ericafenyo.seniorhub.exceptions.invitation.InvitationNotFoundException;
import com.ericafenyo.seniorhub.exceptions.role.InvalidRoleException;
import com.ericafenyo.seniorhub.mapper.InvitationMapper;
import com.ericafenyo.seniorhub.model.Invitation;
import com.ericafenyo.seniorhub.model.Mail;
import com.ericafenyo.seniorhub.model.Report;
import com.ericafenyo.seniorhub.repository.InvitationRepository;
import com.ericafenyo.seniorhub.repository.RoleRepository;
import com.ericafenyo.seniorhub.repository.TeamRepository;
import com.ericafenyo.seniorhub.repository.UserRepository;
import com.ericafenyo.seniorhub.util.Hashing;
import com.ericafenyo.seniorhub.util.Invitations;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static com.ericafenyo.seniorhub.Messages.ERROR_RESOURCE_NOTFOUND;
import static com.ericafenyo.seniorhub.Messages.ERROR_RESOURCE_NOTFOUND_CODE;
import static com.ericafenyo.seniorhub.Messages.MESSAGE_INVITATION_ACCEPTED;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {
    private final EnvironmentVariables environment;
    private final InvitationMapper invitationMapper;
    private final InvitationRepository invitationRepository;
    private final MailService mailService;
    private final UserRepository userRepository;
    private final Messages messages;
    private final TeamRepository teamRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public Report invite(String teamId, String inviterId, String roleSlug, String email) throws HttpException {

        // Get the inviter or throw an error if it does not exist
        var inviter = userRepository.findById(inviterId).orElseThrow(
            () -> new NotFoundException(
                messages.format(Messages.ERROR_RESOURCE_WITH_ID_NOTFOUND, "User", inviterId),
                messages.format(ERROR_RESOURCE_NOTFOUND_CODE, "user")
            )
        );

        // Get the team or throw an error if it does not exist
        var team = teamRepository.findById(teamId).orElseThrow(() -> new NotFoundException(
            messages.format(Messages.ERROR_RESOURCE_WITH_ID_NOTFOUND, "Team", teamId),
            messages.format(ERROR_RESOURCE_NOTFOUND_CODE, "team")
        ));

        var role = roleRepository.findBySlug(roleSlug).orElseThrow(() -> new InvalidRoleException());

        String token = Hashing.randomSHA256();

        var invitation = new InvitationEntity()
            .setToken(token)
            .setEmail(email)
            .setRole(role)
            .setExpiresAt(Instant.now().plusSeconds(environment.getInvitationExpirySeconds()))
            .setTeam(team)
            .setInviter(inviter);

        invitationRepository.save(invitation);

        var baseUrl = environment.getBaseUrl();
        var link = "%s/invitations?token=%s".formatted(baseUrl, token);
        var context = new Mail.Context();
        context.put("link", link);
        return mailService.sendInvitation(email, context);
    }

    @Override
    public Invitation validateInvitation(ValidateInvitationRequest request) throws HttpException {
        var invitation = invitationRepository.findByToken(request.getToken())
            .orElseThrow(() -> new InvitationNotFoundException());

        // Check if the invitation has already been accepted
        if (invitation.getStatus() == Invitation.Status.ACCEPTED) {
            throw new InvitationAlreadyUsedException();
        }

        // Check if the invitation has expired
        if (Invitations.hasExpired(invitation.getExpiresAt())) {
            throw new InvitationExpiredException();
        }

        if (Invitations.hasExpired(invitation.getExpiresAt())) {
            throw new InvitationExpiredException();
        }

        if (invitation.getUsedAt() != null) {
            throw new InvitationAlreadyUsedException();
        }

        return invitationMapper.apply(invitation);
    }

    @Override
    @Transactional
    public Report acceptInvitation(AcceptInvitationRequest request) throws HttpException {
        // Check if the invitation exists
        var invitation = invitationRepository.findByToken(request.getToken())
            .orElseThrow(() -> new NotFoundException(
                messages.format(ERROR_RESOURCE_NOTFOUND, "invitation"),
                messages.format(ERROR_RESOURCE_NOTFOUND_CODE, "invitation")
            ));

        // Check if the invitation has already been accepted
        if (invitation.getStatus() == Invitation.Status.ACCEPTED) {
            throw new InvitationAlreadyUsedException();
        }

        // Check if the invitation has expired
        if (Invitations.hasExpired(invitation.getExpiresAt())) {
            throw new InvitationExpiredException();
        }

        var invitee = userRepository.findByEmail(invitation.getEmail())
            .orElseThrow(() -> new NotFoundException(
                messages.format(ERROR_RESOURCE_NOTFOUND, "invitee"),
                messages.format(ERROR_RESOURCE_NOTFOUND_CODE, "invitee")
            ));

        var team = invitation.getTeam();

        team.getMembers().add(invitee);

        invitation.setUsedAt(Instant.now());
        invitation.setStatus(Invitation.Status.ACCEPTED);

        invitationRepository.save(invitation);

        return new Report(messages.get(MESSAGE_INVITATION_ACCEPTED));
    }
}
