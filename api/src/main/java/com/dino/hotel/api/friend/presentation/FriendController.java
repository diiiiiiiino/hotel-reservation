package com.dino.hotel.api.friend.presentation;

import com.dino.hotel.api.common.http.response.DataResponse;
import com.dino.hotel.api.friend.query.FriendDto;
import com.dino.hotel.api.friend.query.FriendQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friend")
public class FriendController {

    private FriendQueryService friendQueryService;

    @GetMapping
    public DataResponse<List<FriendDto>> getFriends(@RequestParam Long memberId){
        List<FriendDto> allByMemberId = friendQueryService.findAllByMemberId(memberId);

        return DataResponse.ok(allByMemberId);
    }
}
