package com.bb.zavrsni.WebAppApiGateway.controllers.MusicMS;

import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.TrackDto;
import com.bb.zavrsni.WebAppApiGateway.services.interfaces.MusicMS.UserPlayHistory;
import com.bb.zavrsni.WebAppApiGateway.services.interfaces.UserMS.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/play-history")
public class UserPlayHistoryController {

    private final UserPlayHistory userPlayHistory;
    private final UserService userService;

    @Autowired
    public UserPlayHistoryController(UserPlayHistory userPlayHistory, UserService userService) {
        this.userPlayHistory = userPlayHistory;
        this.userService = userService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<TrackDto>> getUserPlayHistory(@AuthenticationPrincipal Jwt jwt) {
        String userId = userService.getUserByAuth0Id(jwt.getSubject()).getId().toString();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userPlayHistory.getUserPlayHistory(userId));

    }

}
